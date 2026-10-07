package com.lulak.frugo.dto.order;

public class AdminOrderItemDetailDto {

    private String orderNumber;
    private String warehouseItemCategoryCode;
    private String warehouseItemType;
    private String warehouseItemName;
    private String etiNumber;
    private Integer quantity;
    private Integer pickedQuantity;
    private String statusCode;

    public AdminOrderItemDetailDto(
            String orderNumber,
            String warehouseItemCategoryCode,
            String warehouseItemType,
            String warehouseItemName,
            String etiNumber,
            Integer quantity,
            Integer pickedQuantity,
            String statusCode
    ){
        this.orderNumber = orderNumber;
        this.warehouseItemCategoryCode = warehouseItemCategoryCode;
        this.warehouseItemType = warehouseItemType;
        this.warehouseItemName = warehouseItemName;
        this.etiNumber = etiNumber;
        this.quantity = quantity;
        this.pickedQuantity = pickedQuantity;
        this.statusCode = statusCode;
    }

    public String getOrderNumber(){ return orderNumber; }
    public String getWarehouseItemCategoryCode(){ return warehouseItemCategoryCode; }
    public String getWarehouseItemType(){ return warehouseItemType; }
    public String getWarehouseItemName(){ return warehouseItemName; }
    public String getEtiNumber(){ return etiNumber; }
    public Integer getQuantity(){ return quantity; }
    public Integer getPickedQuantity(){ return pickedQuantity; }
    public String getStatusCode(){ return statusCode; }
}
