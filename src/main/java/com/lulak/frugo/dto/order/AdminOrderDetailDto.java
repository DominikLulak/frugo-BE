package com.lulak.frugo.dto.order;

import java.time.LocalDateTime;
import java.util.List;

public class AdminOrderDetailDto {

    public Integer id;
    public String orderNumber;

    public Integer customerId;
    public String customerName;

    public String statusCode;
    public String statusName;

    public LocalDateTime createdAt;

    public List<AdminOrderItemDto> items;

    public AdminOrderDetailDto(
            Integer id,
            String orderNumber,
            Integer customerId,
            String customerName,
            String statusCode,
            String statusName,
            LocalDateTime createdAt,
            List<AdminOrderItemDto> items
    ){
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.customerName = customerName;
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.createdAt = createdAt;
        this.items = items;
    }
}
