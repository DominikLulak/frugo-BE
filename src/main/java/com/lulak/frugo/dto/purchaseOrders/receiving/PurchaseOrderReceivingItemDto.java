package com.lulak.frugo.dto.purchaseOrders.receiving;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class PurchaseOrderReceivingItemDto {

    @NotNull
    private Integer purchaseOrderItemId;

    @NotEmpty
    @Valid
    private List<PurchaseOrderReceivingWarehouseItemDto> items;

    public Integer getPurchaseOrderItemId() {
        return purchaseOrderItemId;
    }

    public void setPurchaseOrderItemId(Integer purchaseOrderItemId) {
        this.purchaseOrderItemId = purchaseOrderItemId;
    }

    public List<PurchaseOrderReceivingWarehouseItemDto> getItems() {
        return items;
    }

    public void setItems(List<PurchaseOrderReceivingWarehouseItemDto> items) {
        this.items = items;
    }
}
