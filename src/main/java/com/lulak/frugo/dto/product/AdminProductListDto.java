package com.lulak.frugo.dto.product;

import java.math.BigDecimal;

public class AdminProductListDto {

    private Integer id;
    private String productCode;
    private String categoryCode;
    private String productType;
    private String productName;
    private BigDecimal pricePerUnit;
    private Integer shelfLifeDays;
    private boolean forSale;
    private Long availableStock;
    private Integer reorderPoint;
    private Integer minimumStock;

    public AdminProductListDto(
            Integer id,
            String productCode,
            String categoryCode,
            String productType,
            String productName,
            BigDecimal pricePerUnit,
            Integer shelfLifeDays,
            boolean forSale,
            Long availableStock,
            Integer reorderPoint,
            Integer minimumStock
    ){
        this.id = id;
        this.productCode = productCode;
        this.categoryCode = categoryCode;
        this.productType = productType;
        this.productName = productName;
        this.pricePerUnit = pricePerUnit;
        this.shelfLifeDays = shelfLifeDays;
        this.forSale = forSale;
        this.availableStock = availableStock;
        this.reorderPoint = reorderPoint;
        this.minimumStock = minimumStock;
    }

    public Integer getId(){ return id; }
    public String getProductCode(){ return productCode; }
    public String getCategoryCode(){ return categoryCode; }
    public String getProductType(){ return productType; }
    public String getProductName(){ return productName; }
    public BigDecimal getPricePerUnit(){ return pricePerUnit; }
    public Integer getShelfLifeDays(){ return shelfLifeDays; }
    public boolean isForSale(){ return forSale; }
    public Long getAvailableStock(){ return availableStock; }
    public Integer getReorderPoint(){ return reorderPoint; }
    public Integer getMinimumStock(){ return minimumStock; }
}
