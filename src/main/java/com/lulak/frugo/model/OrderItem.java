package com.lulak.frugo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String category;
    private String name;
    private String variant;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getCategory(){return category;}
    public void setCategory(String category){this.category = category;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getVariant(){return variant;}
    public void setVariant(String variant){this.variant = variant;}

    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity){this.quantity = quantity;}

    public Order getOrder(){return order;}
    public void setOrder(Order order){this.order = order;}
}
