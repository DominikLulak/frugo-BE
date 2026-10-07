package com.lulak.frugo.dto.shipment;

import java.util.List;

public class AdminShipmentDetailDto {

    public String shipmentNumber;
    public String orderNumber;
    public String statusCode;
    public List<AdminShipmentItemDto> pallets;

    public AdminShipmentDetailDto(
            String shipmentNumber,
            String orderNumber,
            String statusCode,
            List<AdminShipmentItemDto> pallets
    ){
        this.shipmentNumber = shipmentNumber;
        this.orderNumber = orderNumber;
        this.statusCode = statusCode;
        this.pallets = pallets;
    }

    public String getShipmentNumber(){ return shipmentNumber; }
    public String getOrderNumber(){ return orderNumber; }
    public String getStatusCode(){ return statusCode; }
    public List<AdminShipmentItemDto> getPallets(){ return pallets; }
}
