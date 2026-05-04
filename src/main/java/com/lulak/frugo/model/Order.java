package com.lulak.frugo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String customerName;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> items;

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getOrderNumber(){return orderNumber;}
    public void setOrderNumber(String orderNumber){this.orderNumber = orderNumber;}

    public OrderStatus getStatus(){return status;}
    public void setStatus(OrderStatus status){this.status = status;}

    public String getCustomerName(){return customerName;}
    public void setCustomerName(String customerName){this.customerName = customerName;}

    public List<OrderItem> getItems(){return items;}
    public void setItems(List<OrderItem> items){this.items = items;}
}
