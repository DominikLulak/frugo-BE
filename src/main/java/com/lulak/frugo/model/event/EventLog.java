package com.lulak.frugo.model.event;

import com.lulak.frugo.model.employee.Employee;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_log")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_log_definition_id", nullable = false)
    private EventLogDefinition eventLogDefinition;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private EventLogStatus status;

    @Column(name = "reference", length = 150)
    private String reference;

    @Column(name = "description", nullable = false)
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data", columnDefinition = "jsonb")
    private String data;

    public Long getId(){ return id; }

    public EventLogDefinition getEventLogDefinition(){ return eventLogDefinition; }
    public void setEventLogDefinition(EventLogDefinition eventLogDefinition){ this.eventLogDefinition = eventLogDefinition; }

    public LocalDateTime getStartedAt(){ return startedAt; }
    public void setStartedAt(LocalDateTime startedAt){ this.startedAt = startedAt; }

    public LocalDateTime getFinishedAt(){ return finishedAt; }
    public void setFinishedAt(LocalDateTime finishedAt){ this.finishedAt = finishedAt; }

    public Employee getEmployee(){ return employee; }
    public void setEmployee(Employee employee){ this.employee = employee; }

    public EventLogStatus getStatus(){ return status; }
    public void setStatus(EventLogStatus status){ this.status = status; }

    public String getReference(){ return reference; }
    public void setReference(String reference){ this.reference = reference; }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }

    public String getData(){ return data; }
    public void setData(String data){ this.data = data; }
}
