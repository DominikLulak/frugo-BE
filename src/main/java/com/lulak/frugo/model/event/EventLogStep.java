package com.lulak.frugo.model.event;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "event_log_step",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_event_log_step_number",
                        columnNames = {"event_log_id", "step_number"}
                )
        }
)
public class EventLogStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_log_id", nullable = false)
    private EventLog eventLog;

    @Column(name = "step_number", nullable = false)
    private Integer stepNumber;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private EventLogStepStatus status;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "error_message")
    private String errorMessage;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data", columnDefinition = "jsonb")
    private String data;

    public Long getId(){ return id; }

    public EventLog getEventLog(){ return eventLog; }
    public void setEventLog(EventLog eventLog){ this.eventLog = eventLog; }

    public Integer getStepNumber(){ return stepNumber; }
    public void setStepNumber(Integer stepNumber){ this.stepNumber = stepNumber; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }

    public LocalDateTime getFinishedAt(){ return finishedAt; }
    public void setFinishedAt(LocalDateTime finishedAt){ this.finishedAt = finishedAt; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }

    public EventLogStepStatus getStatus(){ return status; }
    public void setStatus(EventLogStepStatus status){ this.status = status; }

    public String getErrorCode(){ return errorCode; }
    public void setErrorCode(String errorCode){ this.errorCode = errorCode; }

    public String getErrorMessage(){ return errorMessage; }
    public void setErrorMessage(String errorMessage){ this.errorMessage =  errorMessage; }

    public String getData(){ return data; }
    public void setData(String data){ this.data = data; }
}
