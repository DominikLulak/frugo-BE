package com.lulak.frugo.dto.pallet;

public class AdminPalletWarehouseItemDto {

    private String productCode;
    private String categoryCode;
    private String productType;
    private String productName;
    private String etiNumber;
    private Integer quantity;

    public AdminPalletWarehouseItemDto(
            String productCode,
            String categoryCode,
            String productType,
            String productName,
            String etiNumber,
            Integer quantity
    ){
        this.productCode = productCode;
        this.categoryCode = categoryCode;
        this.productType = productType;
        this.productName = productName;
        this.etiNumber = etiNumber;
        this.quantity = quantity;
    }

    public String getProductCode(){ return productCode; }
    public String getCategoryCode(){ return categoryCode; }
    public String getProductType(){ return productType; }
    public String getProductName(){ return productName; }
    public String getEtiNumber(){ return etiNumber; }
    public Integer getQuantity(){ return quantity; }
}
