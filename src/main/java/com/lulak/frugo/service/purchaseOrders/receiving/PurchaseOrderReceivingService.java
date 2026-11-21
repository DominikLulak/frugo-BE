package com.lulak.frugo.service.purchaseOrders.receiving;

import com.lulak.frugo.dto.purchaseOrders.receiving.PurchaseOrderReceivingDto;
import com.lulak.frugo.dto.purchaseOrders.receiving.PurchaseOrderReceivingItemDto;
import com.lulak.frugo.dto.purchaseOrders.receiving.PurchaseOrderReceivingItemResultDto;
import com.lulak.frugo.dto.purchaseOrders.receiving.PurchaseOrderReceivingResultDto;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.employee.EmployeeLogin;
import com.lulak.frugo.model.product.WarehouseItem;
import com.lulak.frugo.repository.employee.EmployeeLoginRepository;
import com.lulak.frugo.service.event.EventLogTrackingService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PurchaseOrderReceivingService {

    private static final String EVENT_CODE = "PURCHASE_ORDER_RECEIVING_STARTED";

    private final PurchaseOrderReceivingItemService receivingItemService;
    private final EventLogTrackingService eventLogTrackingService;
    private final EmployeeLoginRepository employeeLoginRepository;

    public PurchaseOrderReceivingService(
            PurchaseOrderReceivingItemService receivingItemService,
            EventLogTrackingService eventLogTrackingService,
            EmployeeLoginRepository employeeLoginRepository
    ){
        this.receivingItemService = receivingItemService;
        this.eventLogTrackingService = eventLogTrackingService;
        this.employeeLoginRepository = employeeLoginRepository;
    }

    public PurchaseOrderReceivingResultDto receivedPurchaseOrder(
            PurchaseOrderReceivingDto dto
    ) {
        validateRequest(dto);

        Employee employee = getCurrentEmployee();

        Long eventLogId = eventLogTrackingService.startEvent(
                EVENT_CODE,
                employee,
                dto.getPurchaseOrderId().toString(),
                "Receiving purchase order",
                Map.of(
                        "purchaseOrderId", dto.getPurchaseOrderId(),
                        "itemsCount", dto.getItems().size()
                )
        );

        int successfulItems = 0;
        int failedItems = 0;

        List<PurchaseOrderReceivingItemResultDto> results =
                new ArrayList<>();

        for (PurchaseOrderReceivingItemDto itemDto : dto.getItems()) {

            Long stepId = eventLogTrackingService.startStep(
                    eventLogId,
                    "RECEIVE_PURCHASE_ORDER_ITEM",
                    "Receiving purchase order item "
                            + itemDto.getPurchaseOrderItemId(),
                    Map.of(
                            "purchaseOrderItemId",
                            itemDto.getPurchaseOrderItemId()
                    )
            );

            List<WarehouseItem> createdItems;

            // Business operace
            try {
                createdItems = receivingItemService.receivePurchaseOrderItem(
                        dto.getPurchaseOrderId(),
                        itemDto
                );

            } catch (RuntimeException exception) {

                failedItems++;

                eventLogTrackingService.failStep(
                        stepId,
                        "PURCHASE_ORDER_ITEM_RECEIVING_FAILED",
                        exception.getMessage()
                );

                results.add(new PurchaseOrderReceivingItemResultDto(
                        itemDto.getPurchaseOrderItemId(),
                        "FAILED",
                        List.of(),
                        "PURCHASE_ORDER_ITEM_RECEIVING_FAILED",
                        exception.getMessage()
                ));

                continue;
            }

            // Business operace byla úspěšně dokončena.
            successfulItems++;

            List<Integer> warehouseItemIds = createdItems.stream()
                    .map(WarehouseItem::getId)
                    .toList();

            // Záznam vytvořených entit do EventLogu.
            for (WarehouseItem warehouseItem : createdItems) {
                eventLogTrackingService.addEntity(
                        eventLogId,
                        "WAREHOUSE_ITEM",
                        warehouseItem.getId()
                );
            }

            eventLogTrackingService.completeStep(stepId);

            results.add(new PurchaseOrderReceivingItemResultDto(
                    itemDto.getPurchaseOrderItemId(),
                    "SUCCESS",
                    warehouseItemIds,
                    null,
                    null
            ));
        }

        String overallStatus;

        if (failedItems == 0) {
            overallStatus = "SUCCESS";
            eventLogTrackingService.completeEvent(eventLogId);

        } else if (successfulItems > 0) {
            overallStatus = "PARTIAL_SUCCESS";
            eventLogTrackingService.completeEventWithPartialSuccess(
                    eventLogId
            );

        } else {
            overallStatus = "FAILED";
            eventLogTrackingService.failEvent(eventLogId);
        }

        return new PurchaseOrderReceivingResultDto(
                dto.getPurchaseOrderId(),
                overallStatus,
                dto.getItems().size(),
                successfulItems,
                failedItems,
                results
        );
    }

    private void validateRequest(PurchaseOrderReceivingDto dto){
        if(dto == null){
            throw new IllegalArgumentException(
                    "Receiving request cannot be null"
            );
        }

        if(dto.getPurchaseOrderId() == null){
            throw new IllegalArgumentException(
                    "Purchase order ID is required"
            );
        }

        if(dto.getItems() == null || dto.getItems().isEmpty()){
            throw new IllegalArgumentException(
                    "At least one purchase order item is required"
            );
        }

        Set<Integer> purchaseOrderItemIds = new HashSet<>();

        for (PurchaseOrderReceivingItemDto item : dto.getItems()) {

            if (item == null || item.getPurchaseOrderItemId() == null) {
                throw new IllegalArgumentException(
                        "Purchase order item ID is required"
                );
            }

            if (!purchaseOrderItemIds.add(item.getPurchaseOrderItemId())) {
                throw new IllegalArgumentException(
                        "Duplicate purchase order item ID: "
                                + item.getPurchaseOrderItemId()
                );
            }
        }
    }

    private Employee getCurrentEmployee() {
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
