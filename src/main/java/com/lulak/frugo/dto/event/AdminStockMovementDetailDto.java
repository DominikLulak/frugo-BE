package com.lulak.frugo.dto.event;

import java.time.LocalDateTime;

public class AdminStockMovementDetailDto {

    private Integer id;

    private LocalDateTime createdAt;

    private String eventCode;
    private String eventName;
    private String eventDescription;

    private String etiNumber;
    private String newEtiNumber;

    private Integer quantity;

    private String fromLocation;
    private String toLocation;

    private String employeeNumber;
    private String employeeName;

    private String palletNumber;
    private String orderNumber;
    private String shipmentNumber;
    private String purchaseOrderNumber;

    public AdminStockMovementDetailDto(
            Integer id,
            LocalDateTime createdAt,
            String eventCode,
            String eventName,
            String eventDescription,
            String etiNumber,
            String newEtiNumber,
            Integer quantity,
            String fromLocation,
            String toLocation,
            String employeeNumber,
            String employeeName,
            String palletNumber,
            String orderNumber,
            String shipmentNumber,
            String purchaseOrderNumber
    ){
        this.id = id;
        this.createdAt = createdAt;
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.etiNumber = etiNumber;
        this.newEtiNumber = newEtiNumber;
        this.quantity = quantity;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.palletNumber = palletNumber;
        this.orderNumber = orderNumber;
        this.shipmentNumber = shipmentNumber;
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public Integer getId(){ return id; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public String getEventCode(){ return eventCode; }
    public String getEventName(){ return eventName; }
    public String getEventDescription(){ return eventDescription; }
    public String getEtiNumber(){ return etiNumber; }
    public String getNewEtiNumber(){ return newEtiNumber; }
    public Integer getQuantity(){ return quantity; }
    public String getFromLocation(){ return fromLocation; }
    public String getToLocation(){ return toLocation; }
    public String getEmployeeNumber(){ return employeeNumber; }
    public String getEmployeeName(){ return employeeName; }
    public String getPalletNumber(){ return palletNumber; }
    public String getOrderNumber(){ return orderNumber; }
    public String getShipmentNumber(){ return shipmentNumber; }
    public String getPurchaseOrderNumber(){ return purchaseOrderNumber; }
}
