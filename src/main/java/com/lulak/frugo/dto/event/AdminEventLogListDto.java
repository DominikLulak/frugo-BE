package com.lulak.frugo.dto.event;

import com.lulak.frugo.model.event.EventLogStatus;

import java.time.LocalDateTime;

public class AdminEventLogListDto {

    private final Long id;

    private final String reference;

    private final String eventCode;

    private final LocalDateTime startedAt;
    private final LocalDateTime finishedAt;

    private final String status;

    private final String employeeName;
    private final String description;

    public AdminEventLogListDto(
            Long id,
            String reference,
            String eventCode,
            LocalDateTime startedAt,
            LocalDateTime finishedAt,
            EventLogStatus status,
            String employeeName,
            String description
    ){
        this.id = id;
        this.reference = reference;
        this.eventCode = eventCode;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.status = status.name();
        this.employeeName = employeeName;
        this.description = description;
    }

    public Long getId(){ return id; }
    public String getReference(){ return reference; }
    public String getEventCode(){ return eventCode; }
    public LocalDateTime getStartedAt(){ return startedAt; }
    public LocalDateTime getFinishedAt(){ return finishedAt; }
    public String getStatus(){ return status; }
    public String getEmployeeName(){ return employeeName; }
    public String getDescription(){ return description; }
}
