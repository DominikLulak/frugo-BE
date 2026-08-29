package com.lulak.frugo.dto.order;

import java.time.LocalDateTime;

public class AdminOrderDto {

    public Integer id;
    public String orderNumber;

    public Integer customerId;
    public String customerName;

    public String statusCode;
    public String statusName;

    public LocalDateTime createdAt;

    public AdminOrderDto(
            Integer id,
            String orderNumber,
            Integer customerId,
            String customerName,
            String statusCode,
            String statusName,
            LocalDateTime createdAt
    ){
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.customerName = customerName;
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.createdAt = createdAt;
    }
}
