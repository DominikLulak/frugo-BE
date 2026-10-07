package com.lulak.frugo.dto.shipment;

public class AdminShipmentListDto {

    private Integer id;
    private String shipmentNumber;
    private String orderNumber;
    private String statusCode;

    public AdminShipmentListDto(
            Integer id,
            String shipmentNumber,
            String orderNumber,
            String statusCode
    ){
        this.id = id;
        this.shipmentNumber = shipmentNumber;
        this.orderNumber = orderNumber;
        this.statusCode = statusCode;
    }

    public Integer getId(){ return id; }
    public String getShipmentNumber(){ return shipmentNumber; }
    public String getOrderNumber(){ return orderNumber; }
    public String getStatusCode(){ return statusCode; }
}
