package com.lulak.frugo.dto.purchaseOrders;

public class AdminPurchaseOrderItemDto {

    private String categoryCode;
    private String productType;
    private String productName;
    private String countryCode;
    private Integer quantity;
    private Integer receivedQuantity;
    private String statusCode;

    public AdminPurchaseOrderItemDto(
            String categoryCode,
            String productType,
            String productName,
            String countryCode,
            Integer quantity,
            Integer receivedQuantity,
            String statusCode
    ){
        this.categoryCode = categoryCode;
        this.productType = productType;
        this.productName = productName;
        this.countryCode = countryCode;
        this.quantity = quantity;
        this.receivedQuantity = receivedQuantity;
        this.statusCode = statusCode;
    }

    public String getCategoryCode(){ return categoryCode; }
    public String getProductType(){ return productType; }
    public String getProductName(){ return productName; }
    public String getCountryCode(){ return countryCode; }
    public Integer getQuantity(){ return quantity; }
    public Integer getReceivedQuantity(){return receivedQuantity; }
    public String getStatusCode(){ return statusCode; }

}
