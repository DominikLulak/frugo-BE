package com.lulak.frugo.dto.purchaseOrders.receiving;

import java.util.List;

public class PurchaseOrderReceivingResultDto {

    private Integer purchaseOrderId;
    private String status;

    private int totalItems;
    private int successfulItems;
    private int failedItems;

    private List<PurchaseOrderReceivingItemResultDto> items;

    public PurchaseOrderReceivingResultDto(
            Integer purchaseOrderId,
            String status,
            int totalItems,
            int successfulItems,
            int failedItems,
            List<PurchaseOrderReceivingItemResultDto> items
    ){
        this.purchaseOrderId = purchaseOrderId;
        this.status = status;
        this.totalItems = totalItems;
        this.successfulItems = successfulItems;
        this.failedItems = failedItems;
        this.items = items;
    }

    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getSuccessfulItems() {
        return successfulItems;
    }

    public int getFailedItems() {
        return failedItems;
    }

    public List<PurchaseOrderReceivingItemResultDto> getItems() {
        return items;
    }
}
