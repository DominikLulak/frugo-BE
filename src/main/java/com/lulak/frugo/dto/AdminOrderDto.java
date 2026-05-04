package com.lulak.frugo.dto;

import com.lulak.frugo.model.OrderStatus;

public class AdminOrderDto {

    public String orderNumber;
    public String status;
    public String customerName;

    public AdminOrderDto(String orderNumber, String status, String customerName){
        this.orderNumber = orderNumber;
        this.status = status;
        this.customerName = customerName;
    }
}
