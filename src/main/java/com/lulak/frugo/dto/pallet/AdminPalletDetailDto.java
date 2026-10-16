package com.lulak.frugo.dto.pallet;

import java.util.List;

public class AdminPalletDetailDto {

    private Integer id;
    private String palletNumber;
    private String locationCode;
    private Boolean closed;
    private List<AdminPalletWarehouseItemDto> items;

    public AdminPalletDetailDto(
            Integer id,
            String palletNumber,
            String locationCode,
            Boolean closed,
            List<AdminPalletWarehouseItemDto> items
    ){
        this.id = id;
        this.palletNumber = palletNumber;
        this.locationCode = locationCode;
        this.closed = closed;
        this.items = items;
    }

    public Integer getId(){ return id; }
    public String getPalletNumber(){ return palletNumber; }
    public String getLocationCode(){return locationCode; }
    public Boolean getClosed(){ return closed; }
    public List<AdminPalletWarehouseItemDto> getItems(){ return items; }
}
