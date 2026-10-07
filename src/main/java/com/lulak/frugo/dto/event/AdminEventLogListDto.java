package com.lulak.frugo.dto.event;

import java.time.LocalDateTime;

public class AdminEventLogListDto {

    private final Integer id;
    private final String eventCode;
    private final LocalDateTime createdAt;
    private final String employeeName;
    private final String description;

    public AdminEventLogListDto(
            Integer id,
            String eventCode,
            LocalDateTime createdAt,
            String employeeName,
            String description
    ){
        this.id = id;
        this.eventCode = eventCode;
        this.createdAt = createdAt;
        this.employeeName = employeeName;
        this.description = description;
    }

    public Integer getId(){ return id; }
    public String getEventCode(){ return eventCode; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public String getEmployeeName(){ return employeeName; }
    public String getDescription(){ return description; }
}
