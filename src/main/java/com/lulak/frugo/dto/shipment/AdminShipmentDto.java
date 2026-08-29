package com.lulak.frugo.dto.shipment;

public class AdminShipmentDto {

    public String shipmentNumber;
    public String orderNumber;
    public String status;
    public String customerName;

    public AdminShipmentDto(String shipmentNumber, String orderNumber, String status, String customerName){
        this.shipmentNumber = shipmentNumber;
        this.orderNumber = orderNumber;
        this.status = status;
        this.customerName = customerName;
    }
}
