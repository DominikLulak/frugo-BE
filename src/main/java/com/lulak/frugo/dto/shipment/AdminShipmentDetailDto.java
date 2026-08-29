package com.lulak.frugo.dto.shipment;

import java.util.List;

public class AdminShipmentDetailDto {

    public String shipmentNumber;
    public String orderNumber;
    public String customerName;
    public String status;
    public List<AdminShipmentItemDto> items;

    public AdminShipmentDetailDto(
            String shipmentNumber,
            String orderNumber,
            String customerName,
            String status,
            List<AdminShipmentItemDto> items
    ){
        this.shipmentNumber = shipmentNumber;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.status = status;
        this.items = items;
    }
}
