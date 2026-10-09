package com.lulak.frugo.service.purchaseOrders;

import com.lulak.frugo.dto.purchaseOrders.*;
import com.lulak.frugo.model.Country;
import com.lulak.frugo.model.Status;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.employee.EmployeeLogin;
import com.lulak.frugo.model.product.Product;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrder;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrderItem;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrderStatusHistory;
import com.lulak.frugo.model.purchaseOrders.Supplier;
import com.lulak.frugo.repository.employee.EmployeeLoginRepository;
import com.lulak.frugo.repository.product.ProductRepository;
import com.lulak.frugo.repository.purchaseOrders.PurchaseOrderItemRepository;
import com.lulak.frugo.repository.purchaseOrders.PurchaseOrderRepository;
import com.lulak.frugo.repository.purchaseOrders.SupplierRepository;
import com.lulak.frugo.repository.referenceData.CountryRepository;
import com.lulak.frugo.repository.referenceData.StatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AdminPurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;

    private final SupplierRepository supplierRepository;
    private final EmployeeLoginRepository employeeLoginRepository;
    private final StatusRepository statusRepository;
    private final ProductRepository productRepository;
    private final CountryRepository countryRepository;

    private final PurchaseOrderStatusHistoryService statusHistoryService;

    private PurchaseOrderItem createPurchaseOrderItem(
            PurchaseOrder purchaseOrder,
            Product product,
            Country country,
            Status status,
            Integer quantity
    ){
        PurchaseOrderItem item = new PurchaseOrderItem();

        item.setPurchaseOrder(purchaseOrder);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setReceivedQuantity(0);
        item.setCountry(country);
        item.setStatus(status);

        return item;
    }

    public AdminPurchaseOrderService(
            PurchaseOrderRepository purchaseOrderRepository,
            PurchaseOrderItemRepository purchaseOrderItemRepository,
            SupplierRepository supplierRepository,
            EmployeeLoginRepository employeeLoginRepository,
            StatusRepository statusRepository,
            ProductRepository productRepository,
            CountryRepository countryRepository,
            PurchaseOrderStatusHistoryService statusHistoryService
    ){
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
        this.supplierRepository = supplierRepository;
        this.employeeLoginRepository = employeeLoginRepository;
        this.statusRepository = statusRepository;
        this.productRepository = productRepository;
        this.countryRepository = countryRepository;
        this.statusHistoryService = statusHistoryService;
    }

    public List<AdminPurchaseOrderListDto> getFilteredPurchaseOrders(
            String orderNumber,
            String supplierName,
            String employeeName,
            String statusCode
    ){
        return purchaseOrderRepository.getFilteredPurchaseOrders(
                orderNumber,
                supplierName,
                employeeName,
                statusCode
        );
    }

    public AdminPurchaseOrderDetailDto getPurchaseOrderDetail(
            Integer id
    ){
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findPurchaseOrderById(id);

        if(purchaseOrder == null){
            throw new RuntimeException("Purchase order not found! " + id);
        }

        List<AdminPurchaseOrderItemDto> items =
                purchaseOrderItemRepository.getPurchaseOrderItems(id);

        return new AdminPurchaseOrderDetailDto(
                purchaseOrder.getPurchaseOrderNumber(),
                purchaseOrder.getSupplier().getId(),
                purchaseOrder.getSupplier().getName(),
                purchaseOrder.getSupplier().getInternalCode(),
                purchaseOrder.getCreatedAt(),
                purchaseOrder.getEmployee().getFirstName() + " " + purchaseOrder.getEmployee().getLastName(),
                purchaseOrder.getStatus().getCode(),
                items
        );
    }

    @Transactional
    public PurchaseOrder createPurchaseOrder(
            PurchaseOrderCreateDto dto
    ){
        // Find supplier
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() ->
                            new RuntimeException("Supplier not found: " + dto.getSupplierId()
                            )
                );

        //Supplier must be active
        if(!supplier.isActive()){
            throw new RuntimeException("Supplier not active!");
        }

        //Get username of logged user
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        //Find login
        EmployeeLogin employeeLogin =
                employeeLoginRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException("Login not found: " + username)
                        );

        //Get employee from login
        Employee employee = employeeLogin.getEmployee();

        //Find initial status
        Status orderStatus = statusRepository.findByCode("ENTERED")
                .orElseThrow(() ->
                        new RuntimeException("Purchase order status ENTERED not found")
                );

        //Create purchase order
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setEmployee(employee);
        purchaseOrder.setCreatedAt(LocalDateTime.now());
        purchaseOrder.setStatus(orderStatus);

        //Temp purchaseOrderNumber
        purchaseOrder.setPurchaseOrderNumber(
                "TEMP-" + UUID.randomUUID()
        );

        //First save
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        //Generate purchase order number
        int year = purchaseOrder.getCreatedAt().getYear();

        String purchaseOrderNumber = String.format(
                "PO-%d-%05d",
                year,
                purchaseOrder.getId()
        );

        purchaseOrder.setPurchaseOrderNumber(purchaseOrderNumber);

        //Save purchase order number
        purchaseOrderRepository.save(purchaseOrder);

        //Create purchase order items
        for(PurchaseOrderItemCreateDto itemDto : dto.getItems()){
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDto.getProductId()));

            Country country = countryRepository.findById(itemDto.getCountryId())
                    .orElseThrow(() -> new RuntimeException("Country not found: " + itemDto.getCountryId()));

            Status itemStatus = statusRepository.findByCode("ENTERED")
                    .orElseThrow(() -> new RuntimeException("Purchase order item status ENTERED not found!"));

            PurchaseOrderItem item = createPurchaseOrderItem(
                    purchaseOrder,
                    product,
                    country,
                    itemStatus,
                    itemDto.getQuantity()
            );

            //Create status history
            statusHistoryService.recordStatusChange(
                    purchaseOrder,
                    null,
                    orderStatus,
                    employee,
                    "Vytvoření objednávky"
            );

            purchaseOrderItemRepository.save(item);
        }

        return purchaseOrder;
    }

    @Transactional
    public void updatePurchaseOrderItem(
            Integer purchaseOrderId,
            Integer itemId,
            PurchaseOrderItemUpdateDto dto
    ){
        PurchaseOrder purchaseOrder = purchaseOrderRepository
                .findPurchaseOrderById(purchaseOrderId);

        if(purchaseOrder == null){
            throw new RuntimeException("Purchase order not found! " + purchaseOrderId);
        }

        String statusCode = purchaseOrder.getStatus().getCode();

        if("COMPLETED".equals(statusCode) || "CANCELED".equals(statusCode)){
            throw new RuntimeException("Purchase order cannot be modified in status " + statusCode);
        }

        PurchaseOrderItem item = purchaseOrderItemRepository
                .findByIdAndPurchaseOrderId(itemId, purchaseOrderId)
                .orElseThrow(() -> new RuntimeException("Purchase order item not found " + itemId));

        Country country = countryRepository
                .findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found " + dto.getCountryId()));

        if(dto.getQuantity() < item.getReceivedQuantity()){
            throw new RuntimeException("Quantity cannot be lower than received quantity!");
        }

        item.setQuantity(dto.getQuantity());
        item.setCountry(country);

        purchaseOrderItemRepository.save(item);
    }

    @Transactional
    public void addPurchaseOrderItem(
            Integer purchaseOrderId,
            PurchaseOrderItemCreateDto dto
    ){
        PurchaseOrder purchaseOrder = purchaseOrderRepository
                .findPurchaseOrderById(purchaseOrderId);

        if(purchaseOrder == null){
            throw new RuntimeException("Purchase order not found: " + purchaseOrderId);
        }

        String statusCode = purchaseOrder.getStatus().getCode();

        if("COMPLETED".equals(statusCode) || "CANCELED".equals(statusCode)){
            throw new RuntimeException("Purchase order cannot be modified in status " + statusCode);
        }

        Product product = productRepository
                .findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found " + dto.getProductId()));

        Country country = countryRepository
                .findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found " + dto.getCountryId()));

        Status status = statusRepository
                .findByCode("ENTERED")
                .orElseThrow(() -> new RuntimeException("Status ENTERED not found"));

        PurchaseOrderItem item = createPurchaseOrderItem(
                purchaseOrder,
                product,
                country,
                status,
                dto.getQuantity()
        );

        purchaseOrderItemRepository.save(item);
    }

    @Transactional
    public void deletePurchaseOrderItem(
            Integer purchaseOrderId,
            Integer itemId
    ){
        PurchaseOrder purchaseOrder = purchaseOrderRepository
                .findPurchaseOrderById(purchaseOrderId);

        if(purchaseOrder == null){
            throw new RuntimeException("Purchase order not found! " + purchaseOrderId);
        }

        String statusCode = purchaseOrder.getStatus().getCode();

        if("COMPLETED".equals(statusCode) || "CANCELED".equals(statusCode)){
            throw new RuntimeException("Purchase order cannot be modified din status " + statusCode);
        }

        PurchaseOrderItem item = purchaseOrderItemRepository
                .findByIdAndPurchaseOrderId(itemId, purchaseOrderId)
                .orElseThrow(() -> new RuntimeException("Purchase order item not found " + itemId));

        if(item.getReceivedQuantity() > 0){
            throw new RuntimeException("Purchase order item cannot be deleted because some quantity has already been received");
        }

        purchaseOrderItemRepository.delete(item);
    }

    @Transactional
    public void deletePurchaseOrder(Integer purchaseOrderId){
        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findPurchaseOrderById(purchaseOrderId);

        if(purchaseOrder == null){
            throw new RuntimeException("Purchase order not found " + purchaseOrderId);
        }

        String statusCode = purchaseOrder.getStatus().getCode();

        if(!"ENTERED".equals(statusCode) && !"BLOCKED".equals(statusCode)){
            throw new RuntimeException("Purchase order cannot be deleted in status " + statusCode);
        }

        boolean hasReceivedQuantity =
                purchaseOrderItemRepository
                        .existsByPurchaseOrderIdAndReceivedQuantityGreaterThan(
                                purchaseOrderId,
                                0
                        );
        if(hasReceivedQuantity){
            throw new RuntimeException(
                    "Purchase order cannot be deleted because some quantity has already been received!"
            );
        }

        purchaseOrderItemRepository.deleteByPurchaseOrderId(purchaseOrderId);
        purchaseOrderRepository.delete(purchaseOrder);
    }

    @Transactional
    public void updatePurchaseOrder(
            Integer purchaseOrderId,
            PurchaseOrderUpdateDto dto
    ){
        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findPurchaseOrderById(purchaseOrderId);

        if(purchaseOrder == null){
            throw new RuntimeException("Purchase order not found " + purchaseOrderId);
        }

        String statusCode = purchaseOrder.getStatus().getCode();

        if(!"ENTERED".equals(statusCode) && !"BLOCKED".equals(statusCode)){
            throw new RuntimeException("Purchase order cannot be modified in status " + statusCode);
        }

        boolean hasReceivedItems = purchaseOrderItemRepository
                .existsByPurchaseOrderIdAndReceivedQuantityGreaterThan(purchaseOrderId, 0);

        if(hasReceivedItems){
            throw new RuntimeException("Purchase order cannot be modified because some quantity has already been received!");
        }

        Supplier supplier = supplierRepository
                .findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found " + dto.getSupplierId()));

        if(!supplier.isActive()){
            throw new RuntimeException("Supplier is not active!");
        }

        purchaseOrder.setSupplier(supplier);
        purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public void changePurchasedOrderStatus(
            Integer purchaseOrderId,
            String newStatusCode,
            String note
    ){
        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findPurchaseOrderById(purchaseOrderId);

        if(purchaseOrder == null){
            throw new RuntimeException("Purchase order not found " + purchaseOrderId);
        }

        if(note == null || note.isBlank()){
            throw new RuntimeException("A note is required when changing the purchase order status!");
        }

        Status oldStatus = purchaseOrder.getStatus();
        String oldStatusCode = oldStatus.getCode();

        boolean allowed =
                ("ENTERED".equals(oldStatusCode) && ("BLOCKED".equals(newStatusCode) || "CANCELLED".equals(newStatusCode)))
                || ("BLOCKED".equals(oldStatusCode) && ("CANCELED".equals(newStatusCode) || "ENTERED".equals(newStatusCode)));

        if(!allowed){
            throw new RuntimeException("Status transition not allowed: " + oldStatus + " -> " + newStatusCode);
        }

        Status newStatus = statusRepository.findByCode(newStatusCode)
                .orElseThrow(() -> new RuntimeException("Status not found " + newStatusCode));

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        EmployeeLogin employeeLogin =
                employeeLoginRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("Login to found "+ username));

        Employee employee = employeeLogin.getEmployee();

        purchaseOrder.setStatus(newStatus);
        purchaseOrderRepository.save(purchaseOrder);

        statusHistoryService.recordStatusChange(
                purchaseOrder,
                oldStatus,
                newStatus,
                employee,
                note.trim()
        );
    }
}
