package com.lulak.frugo.service.product.transactions;

import com.lulak.frugo.dto.product.transactions.SplitStockItemDto;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.employee.EmployeeLogin;
import com.lulak.frugo.model.product.WarehouseItem;
import com.lulak.frugo.repository.employee.EmployeeLoginRepository;
import com.lulak.frugo.repository.pallet.PalletWarehouseItemRepository;
import com.lulak.frugo.repository.product.WarehouseItemRepository;
import com.lulak.frugo.service.event.EventLogTrackingService;
import com.lulak.frugo.service.event.StockMovementRecordingService;
import com.lulak.frugo.service.label.EtiNumberService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WarehouseItemSplitService {

    private static final String EVENT_CODE = "WAREHOUSE_ITEM_SPLIT";
    private static final String MOVEMENT_CODE = "SPLIT";

    private final WarehouseItemRepository warehouseItemRepository;
    private final PalletWarehouseItemRepository palletWarehouseItemRepository;
    private final EmployeeLoginRepository employeeLoginRepository;

    private final EtiNumberService etiNumberService;
    private final StockMovementRecordingService stockMovementRecordingService;
    private final EventLogTrackingService eventLogTrackingService;

    public WarehouseItemSplitService(
            WarehouseItemRepository warehouseItemRepository,
            PalletWarehouseItemRepository palletWarehouseItemRepository,
            EmployeeLoginRepository employeeLoginRepository,
            EtiNumberService etiNumberService,
            StockMovementRecordingService stockMovementRecordingService,
            EventLogTrackingService eventLogTrackingService
    ) {
        this.warehouseItemRepository = warehouseItemRepository;
        this.palletWarehouseItemRepository = palletWarehouseItemRepository;
        this.employeeLoginRepository = employeeLoginRepository;
        this.etiNumberService = etiNumberService;
        this.stockMovementRecordingService = stockMovementRecordingService;
        this.eventLogTrackingService = eventLogTrackingService;
    }

    @Transactional
    public WarehouseItem splitWarehouseItem(SplitStockItemDto dto){
        Employee employee = getCurrentEmployee();

        Long eventLogId = eventLogTrackingService.startEvent(
                EVENT_CODE,
                employee,
                dto.eti(),
                "Warehouse item split",
                Map.of(
                        "etiNumber", dto.eti(),
                        "splitQuantity", dto.quantity()
                )
        );

        Long currentStepId = null;

        try {
            currentStepId = eventLogTrackingService.startStep(
                    eventLogId,
                    "VALIDATE_WAREHOUSE_ITEM_SPLIT",
                    "Validate warehouse item and split quantity",
                    Map.of(
                            "etiNumber", dto.eti(),
                            "splitQuantity", dto.quantity()
                    )
            );

            WarehouseItem originalItem = warehouseItemRepository
                    .findByEtiNumberForUpdate(dto.eti())
                    .orElseThrow(() -> new IllegalStateException(
                            "Warehouse item not found " + dto.eti()
                    ));

            validateSplit(originalItem, dto.quantity());

            eventLogTrackingService.completeStep(currentStepId);
            currentStepId = null;

            currentStepId = eventLogTrackingService.startStep(
                    eventLogId,
                    "CREATE_SPLIT_WAREHOUSE_ITEM",
                    "Create new warehouse item from split",
                    Map.of(
                            "originalEti", originalItem.getEtiNumber(),
                            "splitQuantity", dto.quantity()
                    )
            );

            WarehouseItem newItem = createSplitWarehouseItem(
                    originalItem,
                    dto.quantity()
            );

            newItem = warehouseItemRepository.save(newItem);

            eventLogTrackingService.addEntity(
                    eventLogId,
                    "WAREHOUSE_ITEM",
                    newItem.getId()
            );

            eventLogTrackingService.completeStep(currentStepId);
            currentStepId = null;

            currentStepId = eventLogTrackingService.startStep(
                    eventLogId,
                    "UPDATE_ORIGINAL_WAREHOUSE_ITEM",
                    "Decrease quantity of iriginal warehouse item",
                    Map.of(
                            "originalEti", originalItem.getEtiNumber(),
                            "splitQuanityt", dto.quantity()
                    )
            );

            originalItem.setQuantity(
                    originalItem.getQuantity() - dto.quantity()
            );

            warehouseItemRepository.save(originalItem);

            eventLogTrackingService.addEntity(
                    eventLogId,
                    "WAREHOUSE_ITEM",
                    originalItem.getId()
            );

            eventLogTrackingService.completeStep(currentStepId);
            currentStepId = null;

            currentStepId = eventLogTrackingService.startStep(
                    eventLogId,
                    "RECORD_SPLIT_MOVEMENT",
                    "Record warehouse item split movement",
                    Map.of(
                            "originalEti", originalItem.getEtiNumber(),
                            "newEti", newItem.getEtiNumber(),
                            "quantity", dto.quantity()
                    )
            );

            stockMovementRecordingService.recordMovement(
                    MOVEMENT_CODE,
                    originalItem,
                    newItem,
                    dto.quantity(),
                    originalItem.getLocation(),
                    newItem.getLocation(),
                    employee,
                    null,
                    null,
                    null,
                    null
            );

            eventLogTrackingService.completeStep(currentStepId);
            currentStepId = null;

            eventLogTrackingService.completeEvent(eventLogId);

            return newItem;
        }catch (RuntimeException e){
            if(currentStepId != null){
                eventLogTrackingService.failStep(
                        currentStepId,
                        "WAREHOUSE_ITEM_SPLIT_FAILED",
                        e.getMessage()
                );
            }

            eventLogTrackingService.failEvent(eventLogId);

            throw e;
        }
    }

    private void validateSplit(
            WarehouseItem warehouseItem,
            Integer splitQuantity
    ){
        if(splitQuantity == null || splitQuantity <= 0){
            throw new IllegalArgumentException(
                    "Split quantity must be greater than zero"
            );
        }

        if(palletWarehouseItemRepository.existsByWarehouseItem_Id(
                warehouseItem.getId()
        )){
            throw new IllegalStateException(
                    "Warehouse item assigned to a pallet cannot be split"
            );
        }

        int availableQuantity = warehouseItem.getQuantity() - warehouseItem.getAllocatedQuantity();

        if(splitQuantity > availableQuantity){
            throw new IllegalArgumentException(
                    "Split quantity exceeds available quantity. "
                    + "Available: " + availableQuantity
                    + ", requested: " + splitQuantity
            );
        }

        validatePackaging(warehouseItem, splitQuantity);
    }

    private void validatePackaging(
            WarehouseItem warehouseItem,
            Integer splitQuantity
    ){
        Integer quantityPerPackage = warehouseItem.getQuantityPerPackage();
        Integer quantityPerSubpackage = warehouseItem.getQuantityPerSubpackage();

        if(quantityPerPackage == null || quantityPerPackage <= 0){
            throw new IllegalStateException(
                    "Invalid quantity per package"
            );
        }

        int totalGrams = Math.multiplyExact(splitQuantity, 1000);

        if(totalGrams % quantityPerPackage != 0){
            throw new IllegalArgumentException(
                    "Split quantity must be divisible by package quantity"
            );
        }

        if(warehouseItem.getSubpackageType() == null
            && quantityPerSubpackage != null
        ){
            throw new IllegalStateException(
                    "Subpackage quantity requires a subpackaging type"
            );
        }

        if(warehouseItem.getSubpackageType() != null
            && quantityPerSubpackage == null
        ){
            throw new IllegalStateException(
                    "Subpackage quantity is required"
            );
        }

        if(quantityPerSubpackage != null){
            if(quantityPerSubpackage <= 0){
                throw new IllegalStateException(
                        "Invalid quantity per subpackage"
                );
            }

            if(quantityPerSubpackage > quantityPerPackage){
                throw new IllegalStateException(
                        "Subpackage quantity cannot exceed package quantity"
                );
            }

            if(quantityPerPackage % quantityPerSubpackage != 0){
                throw new IllegalStateException(
                        "Package quantity must be divisible by subpackage quantity"
                );
            }
        }
    }

    private WarehouseItem createSplitWarehouseItem(
            WarehouseItem originalItem,
            Integer splitQuantity
    ){
        WarehouseItem newItem = new WarehouseItem();

        newItem.setProduct(originalItem.getProduct());
        newItem.setEtiNumber(etiNumberService.generateWarehouseItemEti());

        newItem.setPackagingType(originalItem.getPackagingType());
        newItem.setQuantityPerPackage(originalItem.getQuantityPerPackage());

        newItem.setSubpackageType(originalItem.getSubpackageType());
        newItem.setQuantityPerSubpackage(originalItem.getQuantityPerSubpackage());

        newItem.setQuantity(splitQuantity);
        newItem.setAllocatedQuantity(0);

        newItem.setCountryOfOrigin(originalItem.getCountryOfOrigin());
        newItem.setReceivedAt(originalItem.getReceivedAt());
        newItem.setExpirationDate(originalItem.getExpirationDate());
        newItem.setLocation(originalItem.getLocation());

        return newItem;
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
}
