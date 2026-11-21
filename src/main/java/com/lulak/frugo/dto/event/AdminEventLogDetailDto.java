package com.lulak.frugo.dto.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AdminEventLogDetailDto {

    private final Long id;

    private final LocalDateTime startedAt;
    private final LocalDateTime finishedAt;

    private final String status;
    private final String reference;

    private final String eventCode;
    private final String eventName;
    private final String eventDescription;

    private final String employeeNumber;
    private final String employeeName;

    private final String description;

    private final Map<String, Object> data;

    private final List<AdminEventLogEntityDto> entities;
    private final List<EventLogStepDto> steps;

    public AdminEventLogDetailDto(
            Long id,
            LocalDateTime startedAt,
            LocalDateTime finishedAt,
            String status,
            String reference,
            String eventCode,
            String eventName,
            String eventDescription,
            String employeeNumber,
            String employeeName,
            String description,
            Map<String, Object> data,
            List<AdminEventLogEntityDto> entities,
            List<EventLogStepDto> steps
    ){
        this.id = id;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.status = status;
        this.reference= reference;
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.description = description;
        this.data = data;
        this.entities = entities;
        this.steps = steps;
    }

    public Long getId(){ return id; }
    public LocalDateTime getStartedAt(){ return startedAt; }
    public LocalDateTime getFinishedAt(){ return finishedAt; }
    public String getStatus(){ return status; }
    public String getReference(){ return reference; }
    public String getEventCode(){ return eventCode; }
    public String getEventName(){ return eventName; }
    public String getEventDescription(){ return eventDescription; }
    public String getEmployeeNumber(){ return employeeNumber; }
    public String getEmployeeName(){ return employeeName; }
    public String getDescription(){ return description; }
    public Map<String, Object> getData(){ return data; }
    public List<AdminEventLogEntityDto> getEntities(){ return entities; }
    public List<EventLogStepDto> getSteps(){ return steps; }
}
