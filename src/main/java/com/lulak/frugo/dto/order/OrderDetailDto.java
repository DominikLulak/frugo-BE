package com.lulak.frugo.dto.order;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailDto {

    private final Integer id;
    private final String orderNumber;
    private final Integer customerId;
    private final String customerName;
    private final LocalDateTime createdAt;
    private final String statusCode;
    private final List<AdminOrderItemDetailDto> items;

    public OrderDetailDto(
            Integer id,
            String orderNumber,
            Integer customerId,
            String customerName,
            LocalDateTime createdAt,
            String statusCode,
            List<AdminOrderItemDetailDto> items
    ){
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.statusCode = statusCode;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public List<AdminOrderItemDetailDto> getItems() {
        return items;
    }
}
