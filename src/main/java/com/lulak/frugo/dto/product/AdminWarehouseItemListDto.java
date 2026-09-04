package com.lulak.frugo.dto.product;

import java.time.LocalDate;

public class AdminWarehouseItemListDto {

    private Integer id;
    private String categoryCode;
    private String productType;
    private String productName;
    private String productCode;
    private String palletNumber;
    private String etiNumber;
    private Integer quantity;
    private Integer allocatedQuantity;
    private Integer availableQuantity;
    private LocalDate expirationDate;
    private String warehouseCode;
    private String locationCode;

    public AdminWarehouseItemListDto(
            Integer id,
            String categoryCode,
            String productType,
            String productName,
            String productCode,
            String palletNumber,
            String etiNumber,
            Integer quantity,
            Integer allocatedQuantity,
            Integer availableQuantity,
            LocalDate expirationDate,
            String warehouseCode,
            String locationCode
    ){
        this.id = id;
        this.categoryCode = categoryCode;
        this.productType = productType;
        this.productName = productName;
        this.productCode = productCode;
        this.palletNumber = palletNumber;
        this.etiNumber = etiNumber;
        this.quantity = quantity;
        this.allocatedQuantity = allocatedQuantity;
        this.availableQuantity = availableQuantity;
        this.expirationDate = expirationDate;
        this.warehouseCode = warehouseCode;
        this.locationCode = locationCode;
    }

    public Integer getId(){ return id; }
    public String getCategoryCode(){ return categoryCode; }
    public String getProductType(){ return productType; }
    public String getProductName(){ return productName; }
    public String getProductCode(){ return productCode; }
    public String getPalletNumber(){ return palletNumber; }
    public String getEtiNumber(){ return etiNumber; }
    public Integer getQuantity(){ return quantity; }
    public Integer getAllocatedQuantity(){ return allocatedQuantity; }
    public Integer getAvailableQuantity(){ return availableQuantity; }
    public LocalDate getExpirationDate(){ return expirationDate; }
    public String getWarehouseCode(){ return warehouseCode; }
    public String getLocationCode(){ return locationCode; }
}
