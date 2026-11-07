package com.lulak.frugo.dto.product.CRUD;

import javax.validation.constraints.NotNull;

public class ProductSaleStatusDto {

    @NotNull
    private Boolean forSale;

    public Boolean getForSale() {
        return forSale;
    }

    public void setForSale(Boolean forSale) {
        this.forSale = forSale;
    }
}
