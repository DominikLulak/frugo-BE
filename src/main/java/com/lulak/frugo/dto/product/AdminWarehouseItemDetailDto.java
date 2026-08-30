package com.lulak.frugo.dto.product;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdminWarehouseItemDetailDto {

    private Integer id;

    private String categoryCode;
    private String productType;
    private String productName;
    private String productCode;

    private String etiNumber;

    private String packagingType;
    private Integer quantityPerPackage;

    private String subpackageType;
    private Integer quantityPerSubpackage;

    private Integer quantity;
    private Integer allocatedQuantity;
    private Integer availableQuantity;

    private String countryOfOrigin;

    private LocalDateTime receivedAt;
    private LocalDate expirationDate;

    private String warehouseCode;
    private String locationCode;

    public AdminWarehouseItemDetailDto(
            Integer id,
            String categoryCode,
            String productType,
            String productName,
            String productCode,
            String etiNumber,
            String packagingType,
            Integer quantityPerPackage,
            String subpackageType,
            Integer quantityPerSubpackage,
            Integer quantity,
            Integer allocatedQuantity,
            Integer availableQuantity,
            String countryOfOrigin,
            LocalDateTime receivedAt,
            LocalDate expirationDate,
            String warehouseCode,
            String locationCode
    ){
        this.id = id;
        this.categoryCode = categoryCode;
        this.productType = productType;
        this.productName = productName;
        this.productCode = productCode;
        this.etiNumber = etiNumber;
        this.packagingType = packagingType;
        this.quantityPerPackage = quantityPerPackage;
        this.subpackageType = subpackageType;
        this.quantityPerSubpackage = quantityPerSubpackage;
        this.quantity = quantity;
        this.allocatedQuantity = allocatedQuantity;
        this.availableQuantity = availableQuantity;
        this.countryOfOrigin = countryOfOrigin;
        this.receivedAt = receivedAt;
        this.expirationDate = expirationDate;
        this.warehouseCode = warehouseCode;
        this.locationCode = locationCode;
    }

    public Integer getId(){ return id; }
    public String getCategoryCode(){ return categoryCode; }
    public String getProductType(){ return productType; }
    public String getProductName(){ return productName; }
    public String getProductCode(){ return productCode; }
    public String getEtiNumber(){ return etiNumber; }
    public String getPackagingType(){ return packagingType; }
    public Integer getQuantityPerPackage(){ return quantityPerPackage; }
    public String getSubpackageType(){ return subpackageType; }
    public Integer getQuantityPerSubpackage(){ return quantityPerSubpackage; }
    public Integer getQuantity(){ return quantity; }
    public Integer getAllocatedQuantity(){ return allocatedQuantity; }
    public Integer getAvailableQuantity(){ return availableQuantity; }
    public String getCountryOfOrigin(){ return countryOfOrigin; }
    public LocalDateTime getReceivedAt(){ return receivedAt; }
    public LocalDate getExpirationDate(){ return expirationDate; }
    public String getWarehouseCode(){ return warehouseCode; }
    public String getLocationCode(){ return locationCode; }
}
