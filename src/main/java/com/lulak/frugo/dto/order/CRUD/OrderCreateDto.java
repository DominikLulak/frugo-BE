package com.lulak.frugo.dto.order.CRUD;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderCreateDto {

    @NotNull
    private Integer customerId;

    @NotEmpty
    @Valid
    private List<OrderItemCreateDto> items;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemCreateDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemCreateDto> items) {
        this.items = items;
    }
}
