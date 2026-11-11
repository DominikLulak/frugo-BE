package com.lulak.frugo.dto.event;

import java.time.LocalDateTime;

public class OrderStatusHistoryListDto {

    private final Integer id;

    private final String orderNumber;

    private final String oldStatusCode;
    private final String newStatusCode;

    private final LocalDateTime changedAt;

    private final String employeeName;
    private final String note;

    public OrderStatusHistoryListDto(
            Integer id,
            String orderNumber,
            String oldStatusCode,
            String newStatusCode,
            LocalDateTime changedAt,
            String employeeName,
            String note
    ){
        this.id = id;
        this.orderNumber = orderNumber;
        this.oldStatusCode = oldStatusCode;
        this.newStatusCode = newStatusCode;
        this.changedAt = changedAt;
        this.employeeName = employeeName;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOldStatusCode() {
        return oldStatusCode;
    }

    public String getNewStatusCode() {
        return newStatusCode;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getNote() {
        return note;
    }
}
