package com.lulak.frugo.dto.order.CRUD;

import javax.validation.constraints.NotNull;

public class OrderUpdateDto {

    @NotNull
    private Integer customerId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
