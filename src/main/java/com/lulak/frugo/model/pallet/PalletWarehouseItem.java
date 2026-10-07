package com.lulak.frugo.model.pallet;

import com.lulak.frugo.model.product.WarehouseItem;
import jakarta.persistence.*;

@Entity
@Table(name = "pallet_warehouse_item")
public class PalletWarehouseItem {

    @EmbeddedId
    private PalletWarehouseItemId id;

    @ManyToOne
    @MapsId("palletId")
    @JoinColumn(name = "pallet_id", nullable = false)
    private Pallet pallet;

    @OneToOne
    @MapsId("warehouseItemId")
    @JoinColumn(name = "warehouse_item_id", nullable = false, unique = true)
    private WarehouseItem warehouseItem;

    public PalletWarehouseItemId getId(){ return id; }
    public void setId(PalletWarehouseItemId id){ this.id = id; }

    public Pallet getPallet(){ return pallet; }
    public void setPallet(Pallet pallet){ this.pallet = pallet; }

    public WarehouseItem getWarehouseItem(){ return warehouseItem; }
    public void setWarehouseItem(WarehouseItem warehouseItem){ this.warehouseItem = warehouseItem; }
}
