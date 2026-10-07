package com.lulak.frugo.dto.event;

import java.time.LocalDateTime;

public class AdminStockMovementListDto {

    private Integer id;
    private String eventCode;
    private String etiNumber;
    private LocalDateTime createdAt;
    private Integer quantity;
    private String fromLocation;
    private String toLocation;
    private String employeeNumber;

    public AdminStockMovementListDto(
            Integer id,
            String eventCode,
            String etiNumber,
            LocalDateTime createdAt,
            Integer quantity,
            String fromLocation,
            String toLocation,
            String employeeNumber
    ){
        this.id = id;
        this.eventCode = eventCode;
        this.etiNumber = etiNumber;
        this.createdAt = createdAt;
        this.quantity = quantity;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.employeeNumber = employeeNumber;
    }

    public Integer getId(){ return id; }
    public String getEventCode(){ return eventCode; }
    public String getEtiNumber(){ return etiNumber; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public Integer getQuantity(){ return quantity; }
    public String getFromLocation(){ return fromLocation; }
    public String getToLocation(){ return toLocation; }
    public String getEmployeeNumber(){ return employeeNumber; }
}
