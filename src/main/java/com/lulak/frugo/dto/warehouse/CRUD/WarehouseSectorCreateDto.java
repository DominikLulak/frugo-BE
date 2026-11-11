package com.lulak.frugo.dto.warehouse.CRUD;

import javax.validation.constraints.NotNull;

public class WarehouseSectorCreateDto {

    @NotNull
    private String code;

    @NotNull
    private String name;

    @NotNull
    private Integer sectorTypeId;

    private String description;

    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public Integer getSectorTypeId(){ return sectorTypeId; }
    public void setSectorTypeId(Integer sectorTypeId){ this.sectorTypeId = sectorTypeId; }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }
}
