package com.lulak.frugo.model.shipment;

import com.lulak.frugo.model.Status;
import com.lulak.frugo.model.pallet.Pallet;
import jakarta.persistence.*;

@Entity
@Table(name = "shipment_item")
public class ShipmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;

    @OneToOne
    @JoinColumn(name = "pallet_id", nullable = false, unique = true)
    private Pallet pallet;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    public Integer getId(){ return id; }

    public Shipment getShipment(){ return shipment; }
    public void setShipment(Shipment shipment){ this.shipment = shipment; }

    public Pallet getPallet(){ return pallet; }
    public void setPallet(Pallet pallet){ this.pallet = pallet; }

    public Status getStatus(){ return status; }
    public void setStatus(Status status){ this.status = status; }
}
