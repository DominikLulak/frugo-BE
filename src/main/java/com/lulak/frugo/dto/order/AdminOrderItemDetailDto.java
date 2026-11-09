package com.lulak.frugo.dto.order;

public class AdminOrderItemDetailDto {

    private Integer id;
    private Integer productId;
    private Integer warehouseItemId;

    private String orderNumber;
    private String warehouseItemCategoryCode;
    private String warehouseItemType;
    private String warehouseItemName;
    private String etiNumber;

    private Integer quantity;
    private Integer pickedQuantity;
    private String statusCode;

    public AdminOrderItemDetailDto(
            Integer id,
            Integer productId,
            Integer warehouseItemId,
            String orderNumber,
            String warehouseItemCategoryCode,
            String warehouseItemType,
            String warehouseItemName,
            String etiNumber,
            Integer quantity,
            Integer pickedQuantity,
            String statusCode
    ) {
        this.id = id;
        this.productId = productId;
        this.warehouseItemId = warehouseItemId;
        this.orderNumber = orderNumber;
        this.warehouseItemCategoryCode = warehouseItemCategoryCode;
        this.warehouseItemType = warehouseItemType;
        this.warehouseItemName = warehouseItemName;
        this.etiNumber = etiNumber;
        this.quantity = quantity;
        this.pickedQuantity = pickedQuantity;
        this.statusCode = statusCode;
    }

    public Integer getId() { return id; }
    public Integer getProductId() { return productId; }
    public Integer getWarehouseItemId() { return warehouseItemId; }
    public String getOrderNumber() { return orderNumber; }
    public String getWarehouseItemCategoryCode() { return warehouseItemCategoryCode; }
    public String getWarehouseItemType() { return warehouseItemType; }
    public String getWarehouseItemName() { return warehouseItemName; }
    public String getEtiNumber() { return etiNumber; }
    public Integer getQuantity() { return quantity; }
    public Integer getPickedQuantity() { return pickedQuantity; }
    public String getStatusCode() { return statusCode; }
}