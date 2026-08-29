package com.lulak.frugo.dto.order;

public class AdminOrderItemDto {

    public Integer id;

    public Integer productId;
    public String productName;

    public Integer quantity;
    public Integer pickedQuantity;

    public String statusCode;
    public String statusName;

    public AdminOrderItemDto(
            Integer id,
            Integer productId,
            String productName,
            Integer quantity,
            Integer pickedQuantity,
            String statusCode,
            String statusName
    ){
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.pickedQuantity = pickedQuantity;
        this.statusCode = statusCode;
        this.statusName = statusName;
    }
}
