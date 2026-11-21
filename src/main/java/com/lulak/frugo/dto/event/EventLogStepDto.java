package com.lulak.frugo.dto.event;

import java.time.LocalDateTime;
import java.util.Map;

public class EventLogStepDto {

    private final Long id;

    private final Integer stepNumber;

    private final LocalDateTime createdAt;
    private final LocalDateTime finishedAt;

    private final String name;
    private final String description;

    private final String status;

    private final String errorCode;
    private final String errorMessage;

    private final Map<String, Object> data;

    public EventLogStepDto(
            Long id,
            Integer stepNumber,
            LocalDateTime createdAt,
            LocalDateTime finishedAt,
            String name,
            String description,
            String status,
            String errorCode,
            String errorMessage,
            Map<String, Object> data
    ){
        this.id = id;
        this.stepNumber = stepNumber;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.name = name;
        this.description = description;
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public Integer getStepNumber() {
        return stepNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
