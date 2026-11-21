package com.lulak.frugo.dto.purchaseOrders.receiving;

import java.util.List;

public class PurchaseOrderReceivingItemResultDto {

    private Integer purchaseOrderItemId;
    private String status;

    private List<Integer> warehouseItemIds;

    private String errorCode;
    private String errorMessage;

    public PurchaseOrderReceivingItemResultDto(Integer purchaseOrderItemId, String status, List<Integer> warehouseItemIds, String errorCode, String errorMessage) {
        this.purchaseOrderItemId = purchaseOrderItemId;
        this.status = status;
        this.warehouseItemIds = warehouseItemIds;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getPurchaseOrderItemId() {
        return purchaseOrderItemId;
    }

    public String getStatus() {
        return status;
    }

    public List<Integer> getWarehouseItemIds() {
        return warehouseItemIds;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
