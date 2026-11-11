package com.lulak.frugo.dto.purchaseOrders;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PurchaseOrderItemUpdateDto {

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private Integer countryId;

    public Integer getQuantity(){ return quantity; }
    public void setQuantity(Integer quantity){ this.quantity = quantity; }

    public Integer getCountryId(){ return countryId; }
    public void setCountryId(Integer countryId){ this.countryId = countryId; }
}
