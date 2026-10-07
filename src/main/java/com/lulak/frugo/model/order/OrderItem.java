package com.lulak.frugo.model.order;

import com.lulak.frugo.model.Status;
import com.lulak.frugo.model.product.WarehouseItem;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "warehouse_item_id", nullable = false)
    private WarehouseItem warehouseItem;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "picked_quantity", nullable = false)
    private Integer pickedQuantity = 0;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    public Integer getId(){ return id; }

    public Order getOrder(){ return order; }
    public void setOrder(Order order){ this.order = order; }

    public WarehouseItem getWarehouseItem(){ return warehouseItem; }
    public void setWarehouseItem(WarehouseItem warehouseItem){ this.warehouseItem = warehouseItem; }

    public Integer getQuantity(){ return quantity; }
    public void setQuantity(Integer quantity){ this.quantity = quantity; }

    public Integer getPickedQuantity(){ return pickedQuantity; }
    public void setPickedQuantity(Integer pickedQuantity){ this.pickedQuantity = pickedQuantity; }

    public Status getStatus(){ return status; }
    public void setStatus(Status status){ this.status = status; }
}
