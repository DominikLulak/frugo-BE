package com.lulak.frugo.dto.warehouse;

import java.util.List;

public class AdminWarehouseSectorDetailDto {

    private Integer id;
    private String warehouseCode;
    private String code;
    private String name;
    private String typeCode;
    private String typeName;
    private String description;
    private List<AdminWarehouseLocationListDto> locations;

    public AdminWarehouseSectorDetailDto(
            Integer id,
            String warehouseCode,
            String code,
            String name,
            String typeCode,
            String typeName,
            String description,
            List<AdminWarehouseLocationListDto> locations
    ){
        this.id = id;
        this.warehouseCode = warehouseCode;
        this.code = code;
        this.name = name;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.description = description;
        this.locations = locations;
    }

    public Integer getId(){ return id; }
    public String getWarehouseCode(){ return warehouseCode; }
    public String getCode(){ return code; }
    public String getName(){ return name; }
    public String getTypeCode(){ return typeCode; }
    public String getTypeName(){ return typeName; }
    public String getDescription(){ return description; }
    public List<AdminWarehouseLocationListDto> getLocations(){ return locations; }
}
