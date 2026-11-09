package com.lulak.frugo.dto.order.CRUD;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderItemUpdateDto {

    @NotNull
    @Min(1)
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
