package com.lulak.frugo.dto;

import com.lulak.frugo.model.OrderStatus;

public class AdminOrderDto {

    public String orderNumber;
    public OrderStatus status;
    public String customerName;

    public AdminOrderDto(String orderNumber, OrderStatus status, String customerName){
        this.orderNumber = orderNumber;
        this.status = status;
        this.customerName = customerName;
    }
}
