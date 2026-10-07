package com.lulak.frugo.dto.event;

public class AdminEventLogEntityDto {

    private final String entityType;
    private final Integer entityId;

    public AdminEventLogEntityDto(
            String entityType,
            Integer entityId
    ){
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public String getEntityType(){ return entityType; }
    public Integer getEntityId(){ return entityId; }
}
