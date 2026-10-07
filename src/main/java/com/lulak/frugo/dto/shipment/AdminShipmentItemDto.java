package com.lulak.frugo.dto.shipment;

public class AdminShipmentItemDto {

    private Integer palletId;
    private String palletNumber;
    private Boolean palletClosed;
    private String statusCode;

    public AdminShipmentItemDto(
            Integer palletId,
            String palletNumber,
            Boolean palletClosed,
            String statusCode
    ){
        this.palletId = palletId;
        this.palletNumber = palletNumber;
        this.palletClosed = palletClosed;
        this.statusCode = statusCode;
    }

    public Integer getPalletId(){ return palletId; }
    public String getPalletNumber(){ return palletNumber; }
    public Boolean getPalletClosed(){ return palletClosed; }
    public String getStatusCode(){ return statusCode; }
}
