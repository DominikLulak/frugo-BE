package com.lulak.frugo.dto.purchaseOrders;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class PurchaseOrderCreateDto {

    @NotNull
    private Integer supplierId;

    @NotEmpty
    @Valid
    private List<PurchaseOrderItemCreateDto> items;

    public Integer getSupplierId(){ return supplierId; }
    public void setSupplierId(Integer supplierId){ this.supplierId = supplierId; }

    public List<PurchaseOrderItemCreateDto> getItems(){ return items; }
    public void setItems(List<PurchaseOrderItemCreateDto> items){ this.items = items; }
}
