package com.lulak.frugo.dto.product;

import java.math.BigDecimal;

public class AdminProductDetailDto {

    public Integer id;
    public String productCode;
    public String name;
    public String productType;
    public String category;
    public BigDecimal pricePerUnit;
    public Integer shelfLifeDays;
    public boolean forSale;

    public AdminProductDetailDto(
            Integer id,
            String productCode,
            String name,
            String productType,
            String category,
            BigDecimal pricePerUnit,
            Integer shelfLifeDays,
            boolean forSale
    ){
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.productType = productType;
        this.category = category;
        this.pricePerUnit = pricePerUnit;
        this.shelfLifeDays = shelfLifeDays;
        this.forSale = forSale;
    }
}
