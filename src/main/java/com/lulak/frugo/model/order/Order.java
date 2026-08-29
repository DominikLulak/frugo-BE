package com.lulak.frugo.model.order;

import com.lulak.frugo.model.customer.Customer;
import com.lulak.frugo.model.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    public Integer getId(){ return id; }

    public String getOrderNumber(){ return orderNumber; }
    public void setOrderNumber(String orderNumber){ this.orderNumber = orderNumber; }

    public Customer getCustomer(){ return customer; }
    public void setCustomer(Customer customer){ this.customer = customer; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }

    public Status getStatus(){ return status; }
    public void setStatus(Status status){ this.status = status; }

    public List<OrderItem> getItems(){ return items; }
    public void setItems(List<OrderItem> items){ this.items = items; }
}
