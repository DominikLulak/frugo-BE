package com.lulak.frugo.model.pallet;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class PalletWarehouseItemId {

    private Integer palletId;
    private Integer warehouseItemId;

    public PalletWarehouseItemId(){ }

    public PalletWarehouseItemId(Integer palletId, Integer warehouseItemId){
        this.palletId = palletId;
        this.warehouseItemId = warehouseItemId;
    }

    public Integer getPalletId() { return palletId; }
    public void setPalletId(Integer palletId){ this.palletId = palletId; }

    public Integer getWarehouseItemId(){ return warehouseItemId; }
    public void setWarehouseItemId(Integer warehouseItemId){ this.warehouseItemId = warehouseItemId; }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof PalletWarehouseItemId that)) return false;

        return Objects.equals(palletId, that.palletId)
                && Objects.equals(warehouseItemId, that.warehouseItemId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(palletId, warehouseItemId);
    }
}
