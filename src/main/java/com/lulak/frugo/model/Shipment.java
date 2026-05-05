package com.lulak.frugo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String shipmentNumber;
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String customerName;

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getShipmentNumber(){return shipmentNumber;}
    public void setShipmentNumber(String shipmentNumber){this.shipmentNumber = shipmentNumber;}

    public String getOrderNumber(){return orderNumber;}
    public void setOrderNumber(String orderNumber){this.orderNumber = orderNumber;}

    public Status getStatus(){return status;}
    public void setStatus(Status status){this.status = status;}

    public String getCustomerName(){return customerName;}
    public void setCustomerName(String customerName){this.customerName = customerName;}
}
