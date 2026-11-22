
package com.lulak.frugo.service.pallet.transactions;

import com.lulak.frugo.dto.pallet.transaction.MovePalletDto;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.order.Order;
import com.lulak.frugo.model.pallet.Pallet;
import com.lulak.frugo.model.pallet.PalletWarehouseItem;
import com.lulak.frugo.model.product.WarehouseItem;
import com.lulak.frugo.model.shipment.Shipment;
import com.lulak.frugo.model.shipment.ShipmentItem;
import com.lulak.frugo.model.warehouse.Location;
import com.lulak.frugo.repository.employee.EmployeeLoginRepository;
import com.lulak.frugo.repository.pallet.PalletRepository;
import com.lulak.frugo.repository.pallet.PalletWarehouseItemRepository;
import com.lulak.frugo.repository.product.WarehouseItemRepository;
import com.lulak.frugo.repository.shipment.ShipmentItemRepository;
import com.lulak.frugo.repository.warehouse.LocationRepository;
import com.lulak.frugo.service.event.EventLogTrackingService;
import com.lulak.frugo.service.event.StockMovementRecordingService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PalletMovementService {

    private static final String EVENT_CODE = "PALLET_RELOCATED";
    private static final String MOVEMENT_CODE = "RELOCATE";

    private final PalletRepository palletRepository;
    private final PalletWarehouseItemRepository palletWarehouseItemRepository;
    private final WarehouseItemRepository warehouseItemRepository;
    private final ShipmentItemRepository shipmentItemRepository;
    private final LocationRepository locationRepository;
    private final EmployeeLoginRepository employeeLoginRepository;
    private final StockMovementRecordingService stockMovementRecordingService;
    private final EventLogTrackingService eventLogTrackingService;

    public PalletMovementService(
            PalletRepository palletRepository,
            PalletWarehouseItemRepository palletWarehouseItemRepository,
            WarehouseItemRepository warehouseItemRepository,
            ShipmentItemRepository shipmentItemRepository,
            LocationRepository locationRepository,
            EmployeeLoginRepository employeeLoginRepository,
            StockMovementRecordingService stockMovementRecordingService,
            EventLogTrackingService eventLogTrackingService
    ) {
        this.palletRepository = palletRepository;
        this.palletWarehouseItemRepository = palletWarehouseItemRepository;
        this.warehouseItemRepository = warehouseItemRepository;
        this.shipmentItemRepository = shipmentItemRepository;
        this.locationRepository = locationRepository;
        this.employeeLoginRepository = employeeLoginRepository;
        this.stockMovementRecordingService = stockMovementRecordingService;
        this.eventLogTrackingService = eventLogTrackingService;
    }

    @Transactional
    public void movePallet(MovePalletDto dto) {
        validateRequest(dto);

        Employee employee = getCurrentEmployee();

        Pallet pallet = palletRepository
                .findByEtiNumberForUpdate(dto.palletEti())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Pallet " + dto.palletEti() + " was not found"
                ));

        // Create main event
        Long eventLogId = eventLogTrackingService.startEvent(
                EVENT_CODE,
                employee,
                dto.palletEti(),
                "Relocation pallet",
                Map.of(
                        "palletEti", dto.palletEti(),
                        "targetLocationCode", dto.targetLocationCode()
                )
        );

        // Register pallet immediately
        eventLogTrackingService.addEntity(
                eventLogId,
                "PALLET",
                pallet.getId()
        );

        try {
            /*
             * PHASE 1: VALIDATION
             */

            // Validate pallet and target location
            Long palletValidationStepId = eventLogTrackingService.startStep(
                    eventLogId,
                    "VALIDATE_PALLET_RELOCATION",
                    "Validating pallet relocation " + pallet.getPalletNumber(),
                    Map.of(
                            "palletEti", pallet.getPalletNumber(),
                            "targetLocationCode", dto.targetLocationCode()
                    )
            );

            Location targetLocation;
            Location currentPalletLocation;
            List<PalletWarehouseItem> palletItems;

            try {
                targetLocation = getTargetLocation(
                        dto.targetLocationCode()
                );

                currentPalletLocation = pallet.getLocation();

                if (currentPalletLocation.getId().equals(targetLocation.getId())) {
                    throw new IllegalStateException(
                            "Pallet is already at the target location"
                    );
                }

                palletItems = palletWarehouseItemRepository
                        .findAllByPallet_Id(pallet.getId());

                if (palletItems.isEmpty()) {
                    throw new IllegalStateException(
                            "Pallet does not contain any warehouse items"
                    );
                }

                eventLogTrackingService.completeStep(
                        palletValidationStepId
                );

            } catch (RuntimeException exception) {
                eventLogTrackingService.failStep(
                        palletValidationStepId,
                        "PALLET_RELOCATION_VALIDATION_FAILED",
                        exception.getMessage()
                );

                throw exception;
            }

            // Validate every warehouse item before moving anything
            for (PalletWarehouseItem palletItem : palletItems) {
                WarehouseItem warehouseItem = palletItem.getWarehouseItem();

                eventLogTrackingService.addEntity(
                        eventLogId,
                        "WAREHOUSE_ITEM",
                        warehouseItem.getId()
                );

                Long validationStepId = eventLogTrackingService.startStep(
                        eventLogId,
                        "VALIDATE_WAREHOUSE_ITEM",
                        "Validating warehouse item " + warehouseItem.getEtiNumber(),
                        Map.of(
                                "eti", warehouseItem.getEtiNumber(),
                                "targetLocationCode", dto.targetLocationCode()
                        )
                );

                try {
                    Location currentLocation = warehouseItem.getLocation();

                    if (!currentLocation.getId().equals(currentPalletLocation.getId())) {
                        throw new IllegalStateException(
                                "Warehouse item " + warehouseItem.getEtiNumber()
                                        + " is not at the pallet's current location"
                        );
                    }

                    if (warehouseItem.getAllocatedQuantity() > 0) {
                        throw new IllegalStateException(
                                "Warehouse item " + warehouseItem.getEtiNumber()
                                        + " has allocated quantity"
                        );
                    }

                    eventLogTrackingService.completeStep(validationStepId);

                } catch (RuntimeException exception) {
                    eventLogTrackingService.failStep(
                            validationStepId,
                            "WAREHOUSE_ITEM_VALIDATION_FAILED",
                            exception.getMessage()
                    );

                    throw exception;
                }
            }

            /*
             * PHASE 2: RELOCATION
             */

            // Find shipment and order associated with the pallet
            Shipment shipment = shipmentItemRepository
                    .findByPalletIdWithShipmentAndOrder(pallet.getId())
                    .map(ShipmentItem::getShipment)
                    .orElse(null);

            Order order = shipment != null
                    ? shipment.getOrder()
                    : null;

            // Move individual warehouse items
            for (PalletWarehouseItem palletItem : palletItems) {
                WarehouseItem warehouseItem = palletItem.getWarehouseItem();

                Location currentLocation = warehouseItem.getLocation();

                Long relocationStepId = eventLogTrackingService.startStep(
                        eventLogId,
                        "RELOCATE_WAREHOUSE_ITEM",
                        "Moving warehouse item " + warehouseItem.getEtiNumber(),
                        Map.of(
                                "eti", warehouseItem.getEtiNumber(),
                                "targetLocationCode", dto.targetLocationCode()
                        )
                );

                try {
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
                            pallet,
                            order,
                            shipment,
                            null
                    );

                    eventLogTrackingService.completeStep(
                            relocationStepId
                    );

                } catch (RuntimeException exception) {
                    eventLogTrackingService.failStep(
                            relocationStepId,
                            "WAREHOUSE_ITEM_RELOCATION_FAILED",
                            exception.getMessage()
                    );

                    throw exception;
                }
            }

            // Move pallet itself
            Long palletStepId = eventLogTrackingService.startStep(
                    eventLogId,
                    "RELOCATE_PALLET",
                    "Moving pallet " + pallet.getPalletNumber(),
                    Map.of(
                            "palletEti", pallet.getPalletNumber(),
                            "targetLocationCode", dto.targetLocationCode()
                    )
            );

            try {
                pallet.setLocation(targetLocation);
                palletRepository.save(pallet);

                eventLogTrackingService.completeStep(palletStepId);

            } catch (RuntimeException exception) {
                eventLogTrackingService.failStep(
                        palletStepId,
                        "PALLET_RELOCATION_FAILED",
                        exception.getMessage()
                );

                throw exception;
            }

            // Complete event
            eventLogTrackingService.completeEvent(eventLogId);

        } catch (RuntimeException exception) {
            eventLogTrackingService.failEvent(eventLogId);
            throw exception;
        }
    }



    private void validateRequest(MovePalletDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        if (dto.palletEti() == null || dto.palletEti().isBlank()) {
            throw new IllegalArgumentException("Pallet ETI must not be empty");
        }

        if (dto.targetLocationCode() == null
                || dto.targetLocationCode().isBlank()) {
            throw new IllegalArgumentException(
                    "Target location code must not be empty"
            );
        }
    }

    private Location getTargetLocation(String targetLocationCode) {
        String[] parts = targetLocationCode.split(";", -1);

        if (parts.length != 3
                || parts[0].isBlank()
                || parts[1].isBlank()
                || parts[2].isBlank()) {
            throw new IllegalArgumentException(
                    "Target location must be in format WAREHOUSE;SECTOR;LOCATION"
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

        if (locations.isEmpty()) {
            throw new IllegalArgumentException(
                    "Target location was not found"
            );
        }

        if (locations.size() > 1) {
            throw new IllegalStateException(
                    "The target location is not unique. Contact administrator!"
            );
        }

        return locations.get(0);
    }

    private Employee getCurrentEmployee() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        return employeeLoginRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalStateException(
                        "Employee login was not found"
                ))
                .getEmployee();
    }
}
