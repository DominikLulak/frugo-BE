package com.lulak.frugo.dto.order;

import java.time.LocalDateTime;

public class AdminOrderListDto {

    private Integer id;
    private String orderNumber;
    private String customerName;
    private LocalDateTime createdAt;
    private String statusCode;

    public AdminOrderListDto(
            Integer id,
            String orderNumber,
            String customerName,
            LocalDateTime createdAt,
            String statusCode
    ){
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.statusCode = statusCode;
    }

    public Integer getId(){ return id; }
    public String getOrderNumber(){ return orderNumber; }
    public String getCustomerName(){ return customerName; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public String getStatusCode(){ return statusCode; }
}
