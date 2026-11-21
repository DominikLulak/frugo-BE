package com.lulak.frugo.dto.purchaseOrders.receiving;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PurchaseOrderReceivingWarehouseItemDto {

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private Integer packagingId;

    @NotNull
    @Positive
    private Integer quantityPerPackage;

    @Positive
    private Integer subpackagingId;

    @Positive
    private Integer quantityPerSubpackage;


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPackagingId() {
        return packagingId;
    }

    public void setPackagingId(Integer packagingId) {
        this.packagingId = packagingId;
    }

    public Integer getQuantityPerPackage() {
        return quantityPerPackage;
    }

    public void setQuantityPerPackage(Integer quantityPerPackage) {
        this.quantityPerPackage = quantityPerPackage;
    }

    public Integer getSubpackagingId() {
        return subpackagingId;
    }

    public void setSubpackagingId(Integer subpackagingId) {
        this.subpackagingId = subpackagingId;
    }

    public Integer getQuantityPerSubpackage() {
        return quantityPerSubpackage;
    }

    public void setQuantityPerSubpackage(Integer quantityPerSubpackage) {
        this.quantityPerSubpackage = quantityPerSubpackage;
    }
}
