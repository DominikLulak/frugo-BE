package com.lulak.frugo.service.purchaseOrders.receiving;

import com.lulak.frugo.dto.purchaseOrders.receiving.PurchaseOrderReceivingItemDto;
import com.lulak.frugo.dto.purchaseOrders.receiving.PurchaseOrderReceivingWarehouseItemDto;
import com.lulak.frugo.model.Status;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.employee.EmployeeLogin;
import com.lulak.frugo.model.product.Packaging;
import com.lulak.frugo.model.product.WarehouseItem;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrder;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrderItem;
import com.lulak.frugo.model.warehouse.Location;
import com.lulak.frugo.repository.employee.EmployeeLoginRepository;
import com.lulak.frugo.repository.product.PackagingRepository;
import com.lulak.frugo.repository.product.WarehouseItemRepository;
import com.lulak.frugo.repository.purchaseOrders.PurchaseOrderItemRepository;
import com.lulak.frugo.repository.purchaseOrders.PurchaseOrderRepository;
import com.lulak.frugo.repository.referenceData.StatusRepository;
import com.lulak.frugo.repository.warehouse.LocationRepository;
import com.lulak.frugo.service.event.StockMovementRecordingService;
import com.lulak.frugo.service.label.EtiNumberService;
import com.lulak.frugo.service.purchaseOrders.PurchaseOrderStatusHistoryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderReceivingItemService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final WarehouseItemRepository warehouseItemRepository;

    private final PackagingRepository packagingRepository;
    private final LocationRepository locationRepository;
    private final StatusRepository statusRepository;
    private final EmployeeLoginRepository employeeLoginRepository;

    private final EtiNumberService etiNumberService;
    private final StockMovementRecordingService stockMovementRecordingService;

    private final PurchaseOrderStatusHistoryService purchaseOrderStatusHistoryService;

    private static final String MAIN_WAREHOUSE_CODE = "MAIN";
    private static final String SPECIAL_SECTOR_CODE = "SPEC";
    private static final String RECEIVING_LOCATION_CODE = "PRIJEM";

    private Location getReceivingLocation(){
        List<Location> locations =
                locationRepository.findAllByCodeAndSector_CodeAndSector_Warehouse_Code(
                        RECEIVING_LOCATION_CODE,
                        SPECIAL_SECTOR_CODE,
                        MAIN_WAREHOUSE_CODE
                );

        if(locations.isEmpty()){
            throw new IllegalStateException(
                    "Receiving location PRIJEM not found in MAIN/SPEC"
            );
        }

        if(locations.size() > 1){
            throw new IllegalStateException(
                    "Multiple receiving locations PRIJEM found in MAIN/SPEC"
            );
        }

        return locations.get(0);
    }

    public PurchaseOrderReceivingItemService(
            PurchaseOrderRepository purchaseOrderRepository,
            PurchaseOrderItemRepository purchaseOrderItemRepository,
            WarehouseItemRepository warehouseItemRepository,
            PackagingRepository packagingRepository,
            LocationRepository locationRepository,
            StatusRepository statusRepository,
            EmployeeLoginRepository employeeLoginRepository,
            EtiNumberService etiNumberService,
            StockMovementRecordingService stockMovementRecordingService,
            PurchaseOrderStatusHistoryService purchaseOrderStatusHistoryService
    ){
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
        this.warehouseItemRepository = warehouseItemRepository;
        this.packagingRepository = packagingRepository;
        this.locationRepository = locationRepository;
        this.statusRepository = statusRepository;
        this.employeeLoginRepository = employeeLoginRepository;
        this.etiNumberService = etiNumberService;
        this.stockMovementRecordingService = stockMovementRecordingService;
        this.purchaseOrderStatusHistoryService = purchaseOrderStatusHistoryService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<WarehouseItem> receivePurchaseOrderItem(
            Integer purchaseOrderId,
            PurchaseOrderReceivingItemDto dto
    ){
        PurchaseOrder purchaseOrder = purchaseOrderRepository
                .findByIdForUpdate(purchaseOrderId)
                .orElseThrow(() -> new IllegalStateException(
                        "Purchase order not found " + purchaseOrderId
                ));

        validatePurchaseOrderStatus(purchaseOrder);

        PurchaseOrderItem purchaseOrderItem = purchaseOrderItemRepository
                .findByIdAndPurchaseOrderIdForUpdate(
                        dto.getPurchaseOrderItemId(),
                        purchaseOrderId
                )
                .orElseThrow(() -> new IllegalStateException(
                        "Purchase order item not found " + dto.getPurchaseOrderItemId()
                ));

        validateReceivingQuantities(purchaseOrderItem, dto.getItems());

        Employee employee = getCurrentEmployee();

        List<WarehouseItem> createdItems = new ArrayList<>();

        LocalDateTime receivedAt = LocalDateTime.now();

        Location receivingLocation = getReceivingLocation();

        for(PurchaseOrderReceivingWarehouseItemDto itemDto : dto.getItems()){
            WarehouseItem warehouseItem = createWarehouseItem(
                    purchaseOrderItem,
                    itemDto,
                    receivedAt,
                    receivingLocation
            );

            warehouseItem = warehouseItemRepository.save(warehouseItem);

            stockMovementRecordingService.recordMovement(
                    "RECEIVE",
                    warehouseItem,
                    null,
                    warehouseItem.getQuantity(),
                    null,
                    warehouseItem.getLocation(),
                    employee,
                    null,
                    null,
                    null,
                    purchaseOrder
            );

            createdItems.add(warehouseItem);
        }

        int receivedQuantity = dto.getItems()
                .stream()
                .mapToInt(PurchaseOrderReceivingWarehouseItemDto::getQuantity)
                .sum();

        int newReceivedQuantity =
                purchaseOrderItem.getReceivedQuantity() + receivedQuantity;

        purchaseOrderItem.setReceivedQuantity(newReceivedQuantity);
        updatePurchaseOrderItemStatus(purchaseOrderItem);
        purchaseOrderItemRepository.save(purchaseOrderItem);

        updatePurchaseOrderStatus(purchaseOrder, employee);
        return createdItems;
    }

    private WarehouseItem createWarehouseItem(
            PurchaseOrderItem purchaseOrderItem,
            PurchaseOrderReceivingWarehouseItemDto dto,
            LocalDateTime receivedAt,
            Location location
    ){
        Packaging packaging = packagingRepository.findById(dto.getPackagingId())
                .orElseThrow(() -> new IllegalStateException(
                        "Packaging not found " + dto.getPackagingId()
                ));

        Packaging subpackaging = null;
        if(dto.getSubpackagingId() != null){
            subpackaging = packagingRepository.findById(dto.getSubpackagingId())
                    .orElseThrow(() -> new IllegalStateException(
                            "Subpackaging not found " + dto.getSubpackagingId()
                    ));
        }

        WarehouseItem warehouseItem = new WarehouseItem();

        warehouseItem.setProduct(purchaseOrderItem.getProduct());
        warehouseItem.setEtiNumber(etiNumberService.generateWarehouseItemEti());

        warehouseItem.setPackagingType(packaging);
        warehouseItem.setQuantityPerPackage(dto.getQuantityPerPackage());

        warehouseItem.setSubpackageType(subpackaging);
        warehouseItem.setQuantityPerSubpackage(dto.getQuantityPerSubpackage());

        warehouseItem.setQuantity(dto.getQuantity());

        warehouseItem.setCountryOfOrigin(purchaseOrderItem.getCountry());

        warehouseItem.setReceivedAt(receivedAt);

        warehouseItem.setExpirationDate(
                receivedAt.toLocalDate().plusDays(
                        purchaseOrderItem.getProduct().getShelfLifeDays()
                )
        );

        warehouseItem.setAllocatedQuantity(0);
        warehouseItem.setLocation(location);

        return warehouseItem;
    }

    private void validatePurchaseOrderStatus(PurchaseOrder purchaseOrder){
        String statusCode = purchaseOrder.getStatus().getCode();

        if(!"ENTERED".equals(statusCode)
            && !"RELEASED".equals(statusCode)){
            throw new IllegalStateException(
                    "Purchase order cannot be received in status " + statusCode
            );
        }
    }

    private void validateReceivingQuantities(
            PurchaseOrderItem purchaseOrderItem,
            List<PurchaseOrderReceivingWarehouseItemDto> items
    ){
        if(items == null || items.isEmpty()){
            throw new IllegalArgumentException(
                    "At least one warehouse item is required"
            );
        }

        int receivingQuantity = 0;

        for(PurchaseOrderReceivingWarehouseItemDto item : items){

            if(item.getQuantity() == null || item.getQuantity() <= 0){
                throw new IllegalArgumentException(
                        "Quantity must be greater than zero"
                );
            }

            if(item.getQuantityPerPackage() == null
                    || item.getQuantityPerPackage() <= 0){
                throw new IllegalArgumentException(
                        "Quantity per package must be greater than zero"
                );
            }

            // Celková hmotnost položky v gramech.
            int totalGrams = Math.multiplyExact(
                    item.getQuantity(),
                    1000
            );

            // Celková hmotnost musí být dělitelná hmotností jednoho balení.
            if(totalGrams % item.getQuantityPerPackage() != 0){
                throw new IllegalArgumentException(
                        "Total quantity must be divisible by quantity per package"
                );
            }

            validatePackaging(item);

            receivingQuantity = Math.addExact(
                    receivingQuantity,
                    item.getQuantity()
            );
        }

        int remainingQuantity =
                purchaseOrderItem.getQuantity()
                        - purchaseOrderItem.getReceivedQuantity();

        if(receivingQuantity > remainingQuantity){
            throw new IllegalStateException(
                    "Receiving quantity exceeds remaining purchase order quantity. "
                            + "Remaining: " + remainingQuantity
                            + ", requested: " + receivingQuantity
            );
        }
    }

    private void validatePackaging(
            PurchaseOrderReceivingWarehouseItemDto dto
    ){
        Integer subpackagingId = dto.getSubpackagingId();
        Integer quantityPerPackage = dto.getQuantityPerPackage();
        Integer quantityPerSubpackage = dto.getQuantityPerSubpackage();

        // Bez podbalení nesmí být zadána jeho hmotnost.
        if(subpackagingId == null && quantityPerSubpackage != null){
            throw new IllegalArgumentException(
                    "Subpackage quantity requires a subpackaging type"
            );
        }

        // Pokud je vybráno podbalení, musí mít definovanou hmotnost.
        if(subpackagingId != null && quantityPerSubpackage == null){
            throw new IllegalArgumentException(
                    "Subpackage quantity is required"
            );
        }

        if(quantityPerSubpackage != null){

            if(quantityPerSubpackage <= 0){
                throw new IllegalArgumentException(
                        "Quantity per subpackage must be greater than zero"
                );
            }

            // Obě hodnoty jsou již v gramech.
            int packageGrams = quantityPerPackage;

            if(quantityPerSubpackage > packageGrams){
                throw new IllegalArgumentException(
                        "Subpackage quantity cannot exceed package quantity"
                );
            }

            // Hmotnost hlavního balení musí být dělitelná
            // hmotností jednoho podbalení.
            if(packageGrams % quantityPerSubpackage != 0){
                throw new IllegalArgumentException(
                        "Package quantity must be divisible by subpackage quantity"
                );
            }
        }
    }

    private Employee getCurrentEmployee(){
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        EmployeeLogin employeeLogin = employeeLoginRepository
                .findByUsername(username)
                .orElseThrow(() -> new IllegalStateException(
                        "Employee login not found " + username
                ));

        return employeeLogin.getEmployee();
    }

    private void updatePurchaseOrderItemStatus(
            PurchaseOrderItem purchaseOrderItem
    ){
        String statusCode;

        if(purchaseOrderItem.getReceivedQuantity() == 0){
            statusCode = "ENTERED";
        }else if(purchaseOrderItem.getReceivedQuantity() < purchaseOrderItem.getQuantity()){
            statusCode = "RELEASED";
        }else{
            statusCode = "COMPLETED";
        }

        Status status = statusRepository.findByCode(statusCode)
                .orElseThrow(() -> new IllegalStateException(
                        "Status not found " + statusCode
                ));
        purchaseOrderItem.setStatus(status);
    }

    private void updatePurchaseOrderStatus(
            PurchaseOrder purchaseOrder,
            Employee employee
    ){
        List<PurchaseOrderItem> items =
                purchaseOrderItemRepository.findAllByPurchaseOrderIdForUpdate(
                        purchaseOrder.getId()
                );

        boolean allFinished = items.stream()
                .allMatch(item -> item.getReceivedQuantity().equals(item.getQuantity()));

        boolean anyReceived = items.stream()
                .anyMatch(item -> item.getReceivedQuantity() > 0);

        String statusCode;

        if(allFinished){
            statusCode = "COMPLETED";
        }else if(anyReceived){
            statusCode = "RELEASED";
        }else {
            statusCode = "ENTERED";
        }

        Status newStatus = statusRepository.findByCode(statusCode)
                .orElseThrow(() -> new IllegalStateException(
                        "Status not found " + statusCode
                ));
        Status oldStatus = purchaseOrder.getStatus();

        if(!oldStatus.getCode().equals(newStatus.getCode())){
            purchaseOrder.setStatus(newStatus);
            purchaseOrderRepository.save(purchaseOrder);

            purchaseOrderStatusHistoryService.recordStatusChange(
                    purchaseOrder,
                    oldStatus,
                    newStatus,
                    employee,
                    "Purchase order status updated automatically after receiving goods"
            );
        }
    }
}
