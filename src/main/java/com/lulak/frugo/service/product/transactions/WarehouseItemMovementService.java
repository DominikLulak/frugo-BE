package com.lulak.frugo.service.product.transactions;

import com.lulak.frugo.dto.product.transactions.MoveStockItemDto;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.employee.EmployeeLogin;
import com.lulak.frugo.model.product.WarehouseItem;
import com.lulak.frugo.model.warehouse.Location;
import com.lulak.frugo.repository.employee.EmployeeLoginRepository;
import com.lulak.frugo.repository.pallet.PalletWarehouseItemRepository;
import com.lulak.frugo.repository.product.WarehouseItemRepository;
import com.lulak.frugo.repository.warehouse.LocationRepository;
import com.lulak.frugo.service.event.EventLogTrackingService;
import com.lulak.frugo.service.event.StockMovementRecordingService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WarehouseItemMovementService {

    private static final String EVENT_CODE = "WAREHOUSE_ITEM_RELOCATED";
    private static final String MOVEMENT_CODE = "RELOCATE";

    private final WarehouseItemRepository warehouseItemRepository;
    private final LocationRepository locationRepository;
    private final PalletWarehouseItemRepository palletWarehouseItemRepository;
    private final EmployeeLoginRepository employeeLoginRepository;
    private final StockMovementRecordingService stockMovementRecordingService;
    private final EventLogTrackingService eventLogTrackingService;

    public WarehouseItemMovementService(
            WarehouseItemRepository warehouseItemRepository,
            LocationRepository locationRepository,
            PalletWarehouseItemRepository palletWarehouseItemRepository,
            EmployeeLoginRepository employeeLoginRepository,
            StockMovementRecordingService stockMovementRecordingService,
            EventLogTrackingService eventLogTrackingService
    ){
        this.warehouseItemRepository = warehouseItemRepository;
        this.locationRepository = locationRepository;
        this.palletWarehouseItemRepository = palletWarehouseItemRepository;
        this.employeeLoginRepository = employeeLoginRepository;
        this.stockMovementRecordingService = stockMovementRecordingService;
        this.eventLogTrackingService = eventLogTrackingService;
    }

    @Transactional
    public void moveWarehouseItem(MoveStockItemDto dto){
        validateRequest(dto);

        Employee employee = getCurrentEmployee();

        Long eventLogId = eventLogTrackingService.startEvent(
                EVENT_CODE,
                employee,
                dto.eti(),
                "Relocation warehouse item",
                Map.of(
                        "eti", dto.eti(),
                        "targetLocationCode", dto.targetLocationCode()
                )
        );

        Long stepId = eventLogTrackingService.startStep(
                eventLogId,
                "RELOCATE_WAREHOUSE_ITEM",
                "Moving warehouse item " + dto.eti(),
                Map.of(
                        "eti", dto.eti(),
                        "targetLocationCode", dto.targetLocationCode()
                )
        );

        try{
            WarehouseItem warehouseItem = warehouseItemRepository
                    .findByEtiNumberForUpdate(dto.eti())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Eti " + dto.eti() + " was not found"
                    ));

            validateWarehouseItem(warehouseItem);

            Location targetLocation = getTargetLocation(
                    dto.targetLocationCode()
            );

            Location currentLocation = warehouseItem.getLocation();

            if(currentLocation.getId().equals(targetLocation.getId())){
                throw new IllegalStateException(
                        "Warehouse item is already at location"
                );
            }

            warehouseItem.setLocation(targetLocation);
            warehouseItemRepository.save(warehouseItem);

            stockMovementRecordingService.recordMovement(
                    MOVEMENT_CODE,
                    warehouseItem,
                    null,
                    warehouseItem.getQuantity(),
                    currentLocation,
                    targetLocation,
                    employee,
                    null,
                    null,
                    null,
                    null
            );

            eventLogTrackingService.addEntity(
                    eventLogId,
                    "WAREHOUSE_ITEM",
                    warehouseItem.getId()
            );

            eventLogTrackingService.completeStep(stepId);
            eventLogTrackingService.completeEvent(eventLogId);
        }catch (RuntimeException exception){
            eventLogTrackingService.failStep(
                    stepId,
                    "WAREHOUSE_ITEM_RELOCATION_FAILED",
                    exception.getMessage()
            );

            eventLogTrackingService.failEvent(eventLogId);
            throw exception;
        }
    }

    private void validateRequest(MoveStockItemDto dto){
        if(dto == null){
            throw new IllegalArgumentException(
                    "Item movement can't be null"
            );
        }
        if(dto.eti() == null || dto.eti().isBlank()){
            throw new IllegalArgumentException(
                    "ETI cannot be null"
            );
        }

        if(dto.targetLocationCode() == null || dto.targetLocationCode().isBlank()){
            throw new IllegalArgumentException(
                    "Location cannot be null"
            );
        }
    }

    private void validateWarehouseItem(WarehouseItem warehouseItem){
        if(palletWarehouseItemRepository.existsByWarehouseItem_Id(warehouseItem.getId())){
            throw new IllegalStateException(
                    "Warehouse item is assigned to a pallet"
            );
        }

        if(warehouseItem.getAllocatedQuantity() != null && warehouseItem.getAllocatedQuantity() > 0){
            throw new IllegalStateException(
                    "Warehouse item has allocation for order"
            );
        }
    }

    private Location getTargetLocation(String targetLocationCode){
        String[] parts = targetLocationCode.split(";", -1);

        if(parts.length != 3
                || parts[0].isBlank()
                || parts[1].isBlank()
                || parts[2].isBlank()
        ){
            throw new IllegalArgumentException(
                    "Target location must be in in format WAREHOUSE;SECTOR;LOCATION"
            );
        }

        String warehouseCode = parts[0].trim();
        String sectorCode = parts[1].trim();
        String locationCode = parts[2].trim();

        List<Location> locations =
                locationRepository
                        .findAllByCodeAndSector_CodeAndSector_Warehouse_Code(
                                locationCode,
                                sectorCode,
                                warehouseCode
                        );

        if(locations.isEmpty()){
            throw new IllegalArgumentException(
                    "Target location was not found"
            );
        }
        if(locations.size() > 1){
            throw new IllegalStateException(
                    "The target location is not unique. Contact administrator!"
            );
        }

        return locations.get(0);
    }

    private Employee getCurrentEmployee(){
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        EmployeeLogin employeeLogin = employeeLoginRepository
                .findByUsername(username)
                .orElseThrow(() -> new IllegalStateException(
                        "Employee login not found: " + username
                ));

        return employeeLogin.getEmployee();
    }
}
