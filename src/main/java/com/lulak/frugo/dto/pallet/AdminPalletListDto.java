package com.lulak.frugo.dto.pallet;

public class AdminPalletListDto {

    private Integer id;
    private String palletNumber;
    private String locationCode;
    private Boolean closed;

    public AdminPalletListDto(
            Integer id,
            String palletNumber,
            String locationCode,
            Boolean closed
    ){
        this.id = id;
        this.palletNumber = palletNumber;
        this.locationCode = locationCode;
        this.closed = closed;
    }

    public Integer getId(){ return id; }
    public String getPalletNumber(){ return palletNumber; }
    public String getLocationCode(){return locationCode; }
    public Boolean getClosed(){ return closed; }
}
