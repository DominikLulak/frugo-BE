package com.lulak.frugo.dto.order.CRUD;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderItemCreateDto {

    @NotNull
    private Integer productId;

    @NotNull
    @Min(1)
    private Integer quantity;

    public Integer getProductId(){ return productId; }
    public void setProductId(Integer productId){ this.productId = productId; }

    public Integer getQuantity(){ return quantity; }
    public void setQuantity(Integer quantity){ this.quantity = quantity; }
}
