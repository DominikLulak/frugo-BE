package com.lulak.frugo.model.event;

import jakarta.persistence.*;

@Entity
@Table(
        name = "event_log_entity",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_event_log_entity",
                        columnNames = {"event_log_id", "entity_type", "entity_id"}
                )
        }
)
public class EventLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_log_id", nullable = false)
    private EventLog eventLog;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private Integer entityId;

    public Long getId(){ return id; }

    public EventLog getEventLog(){ return eventLog; }
    public void setEventLog(EventLog eventLog){ this.eventLog = eventLog; }

    public String getEntityType(){ return entityType; }
    public void setEntityType(String entityType){ this.entityType = entityType; }

    public Integer getEntityId(){ return entityId; }
    public void setEntityId(Integer entityId){ this.entityId = entityId; }
}
