package com.lulak.frugo.dto.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AdminEventLogDetailDto {

    private final Integer id;

    private final LocalDateTime createdAt;

    private final String eventCode;
    private final String eventName;
    private final String eventDescription;

    private final String employeeNumber;
    private final String employeeName;

    private final String description;

    private final Map<String, Object> data;

    private List<AdminEventLogEntityDto> entities;

    public AdminEventLogDetailDto(
            Integer id,
            LocalDateTime createdAt,
            String eventCode,
            String eventName,
            String eventDescription,
            String employeeNumber,
            String employeeName,
            String description,
            Map<String, Object> data,
            List<AdminEventLogEntityDto> entities
    ){
        this.id = id;
        this.createdAt = createdAt;
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.description = description;
        this.data = data;
        this.entities = entities;
    }

    public Integer getId(){ return id; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public String getEventCode(){ return eventCode; }
    public String getEventName(){ return eventName; }
    public String getEventDescription(){ return eventDescription; }
    public String getEmployeeNumber(){ return employeeNumber; }
    public String getEmployeeName(){ return employeeName; }
    public String getDescription(){ return description; }
    public Map<String, Object> getData(){ return data; }
    public List<AdminEventLogEntityDto> getEntities(){ return entities; }
}
