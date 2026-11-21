package com.lulak.frugo.dto.purchaseOrders.receiving;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class PurchaseOrderReceivingDto {

    @NotNull
    private Integer purchaseOrderId;

    @NotEmpty
    @Valid
    private List<PurchaseOrderReceivingItemDto> items;

    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Integer purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public List<PurchaseOrderReceivingItemDto> getItems() {
        return items;
    }

    public void setItems(List<PurchaseOrderReceivingItemDto> items) {
        this.items = items;
    }
}
