package com.lulak.frugo.model.shipment;

import com.lulak.frugo.model.Status;
import com.lulak.frugo.model.order.Order;
import jakarta.persistence.*;

@Entity
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String shipmentNumber;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    public Integer getId(){ return id; }

    public String getShipmentNumber(){ return shipmentNumber; }
    public void setShipmentNumber(String shipmentNumber){ this.shipmentNumber = shipmentNumber; }

    public Order getOrder(){ return order; }
    public void setOrder(Order order){ this.order = order; }

    public Status getStatus(){ return status; }
    public void setStatus(Status status){ this.status = status; }
}
