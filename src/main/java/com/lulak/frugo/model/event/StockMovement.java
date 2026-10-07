package com.lulak.frugo.model.event;

import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.order.Order;
import com.lulak.frugo.model.pallet.Pallet;
import com.lulak.frugo.model.product.WarehouseItem;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrder;
import com.lulak.frugo.model.shipment.Shipment;
import com.lulak.frugo.model.warehouse.Location;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movement")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "event_definition_id", nullable = false)
    private EventDefinition eventDefinition;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "warehouse_item_id", nullable = false)
    private WarehouseItem warehouseItem;

    @ManyToOne
    @JoinColumn(name = "new_warehouse_item_id")
    private WarehouseItem newWarehouseItem;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "from_location_id")
    private Location fromLocation;

    @ManyToOne
    @JoinColumn(name = "to_location_id")
    private Location toLocation;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "pallet_id")
    private Pallet pallet;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    public Integer getId(){ return id; }

    public EventDefinition getEventDefinition(){ return eventDefinition; }
    public void setEventDefinition(EventDefinition eventDefinition){ this.eventDefinition = eventDefinition; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }

    public PurchaseOrder getPurchaseOrder(){ return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder){ this.purchaseOrder = purchaseOrder; }

    public WarehouseItem getWarehouseItem(){ return warehouseItem; }
    public void setWarehouseItem(WarehouseItem warehouseItem){ this.warehouseItem = warehouseItem; }

    public WarehouseItem getNewWarehouseItem(){ return newWarehouseItem; }
    public void setNewWarehouseItem(WarehouseItem newWarehouseItem){ this.newWarehouseItem = newWarehouseItem; }

    public Integer getQuantity(){ return quantity; }
    public void setQuantity(Integer quantity){ this.quantity = quantity; }

    public Location getFromLocation(){ return fromLocation; }
    public void setFromLocation(Location fromLocation){ this.fromLocation = fromLocation; }

    public Location getToLocation(){ return toLocation; }
    public void setToLocation(Location toLocation){ this.toLocation = toLocation; }

    public Employee getEmployee(){ return employee; }
    public void setEmployee(Employee employee){ this.employee = employee; }

    public Pallet getPallet(){ return pallet; }
    public void setPallet(Pallet pallet){ this.pallet = pallet; }

    public Order getOrder(){ return order; }
    public void setOrder(Order order){ this.order = order; }

    public Shipment getShipment(){ return shipment; }
    public void setShipment(Shipment shipment){ this.shipment = shipment; }
}
