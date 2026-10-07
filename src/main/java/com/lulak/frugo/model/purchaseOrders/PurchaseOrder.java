package com.lulak.frugo.model.purchaseOrders;

import com.lulak.frugo.model.Status;
import com.lulak.frugo.model.employee.Employee;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrder{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purchase_order_number", nullable = false, unique = true)
    private String purchaseOrderNumber;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    public Integer getId(){ return id; }

    public String getPurchaseOrderNumber(){ return purchaseOrderNumber; }
    public void setPurchaseOrderNumber(String purchaseOrderNumber){ this.purchaseOrderNumber = purchaseOrderNumber; }

    public Supplier getSupplier(){ return supplier; }
    public void setSupplier(Supplier supplier){ this.supplier = supplier; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }

    public Employee getEmployee(){ return employee; }
    public void setEmployee(Employee employee){ this.employee = employee; }

    public Status getStatus(){ return status; }
    public void setStatus(Status status){ this.status = status; }
}
