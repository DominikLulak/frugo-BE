package com.lulak.frugo.model.event;

import com.lulak.frugo.model.employee.Employee;
import jakarta.persistence.*;
import tools.jackson.databind.JsonNode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_log")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "event_definition_id", nullable = false)
    private EventDefinition eventDefinition;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "description")
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String data;

    public Integer getId(){ return id; }

    public EventDefinition getEventDefinition(){ return eventDefinition; }
    public void setEventDefinition(EventDefinition eventDefinition){ this.eventDefinition = eventDefinition; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }

    public Employee getEmployee(){ return employee; }
    public void setEmployee(Employee employee){ this.employee = employee; }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }

    public String getData(){ return data; }
    public void setData(String data){ this.data = data; }
}
