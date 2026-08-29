package com.lulak.frugo.dto.shipment;

public class AdminShipmentItemDto {

    public String palletNumber;
    public String status;

    public AdminShipmentItemDto(
            String palletNumber,
            String status
    ){
        this.palletNumber = palletNumber;
        this. status = status;
    }
}
