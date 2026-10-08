package com.lulak.frugo.dto.purchaseOrders;

public class AdminPurchaseOrderItemDto {

    private Integer id;
    private String categoryCode;
    private String productType;
    private String productName;
    private Integer countryId;
    private String countryCode;
    private String countryName;
    private Integer quantity;
    private Integer receivedQuantity;
    private String statusCode;

    public AdminPurchaseOrderItemDto(
            Integer id,
            String categoryCode,
            String productType,
            String productName,
            Integer countryId,
            String countryCode,
            String countryName,
            Integer quantity,
            Integer receivedQuantity,
            String statusCode
    ){
        this.id = id;
        this.categoryCode = categoryCode;
        this.productType = productType;
        this.productName = productName;
        this.countryId = countryId;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.quantity = quantity;
        this.receivedQuantity = receivedQuantity;
        this.statusCode = statusCode;
    }

    public Integer getId(){ return id; }
    public String getCategoryCode(){ return categoryCode; }
    public String getProductType(){ return productType; }
    public String getProductName(){ return productName; }
    public Integer getCountryId(){ return countryId; }
    public String getCountryCode(){ return countryCode; }
    public String getCountryName(){ return countryName; }
    public Integer getQuantity(){ return quantity; }
    public Integer getReceivedQuantity(){return receivedQuantity; }
    public String getStatusCode(){ return statusCode; }

}
