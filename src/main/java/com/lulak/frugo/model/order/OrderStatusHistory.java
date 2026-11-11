package com.lulak.frugo.model.order;

import com.lulak.frugo.model.Status;
import com.lulak.frugo.model.employee.Employee;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_status_history")
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "old_status_id")
    private Status oldStatus;

    @ManyToOne
    @JoinColumn(name = "new_status_id")
    private Status newStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String note;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt = LocalDateTime.now();

    public Integer getId(){ return id; }

    public Order getOrder(){ return order; }
    public void setOrder(Order order){ this.order = order; }

    public Status getOldStatus(){ return oldStatus; }
    public void setOldStatus(Status oldStatus){ this.oldStatus = oldStatus; }

    public Status getNewStatus(){ return newStatus; }
    public void setNewStatus(Status newStatus){ this.newStatus = newStatus; }

    public Employee getEmployee(){ return employee; }
    public void setEmployee(Employee employee){ this.employee = employee; }

    public String getNote(){ return note; }
    public void setNote(String note){ this.note = note; }

    public LocalDateTime getChangedAt(){ return changedAt; }
    public void setChangedAt(LocalDateTime changedAt){ this.changedAt = changedAt; }
}
