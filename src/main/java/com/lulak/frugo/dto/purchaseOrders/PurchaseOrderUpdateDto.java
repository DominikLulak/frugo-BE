package com.lulak.frugo.dto.purchaseOrders;

import javax.validation.constraints.NotNull;

public class PurchaseOrderUpdateDto {

    @NotNull
    private Integer supplierId;

    public Integer getSupplierId(){ return supplierId; }
    public void setSupplierId(Integer supplierId){ this.supplierId = supplierId; }
}
