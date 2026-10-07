package com.lulak.frugo.dto.warehouse;

import java.util.List;

public class AdminWarehouseDetailDto {

    private Integer id;
    private String code;
    private String name;
    private String description;
    private List<AdminWarehouseSectorListDto> sectors;

    public AdminWarehouseDetailDto(
            Integer id,
            String code,
            String name,
            String description,
            List<AdminWarehouseSectorListDto> sectors
    ){
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.sectors = sectors;
    }

    public Integer getId(){ return id; }
    public String getCode(){ return code; }
    public String getName(){ return name; }
    public String getDescription(){ return description; }
    public List<AdminWarehouseSectorListDto> getSectors(){ return sectors; }
}
