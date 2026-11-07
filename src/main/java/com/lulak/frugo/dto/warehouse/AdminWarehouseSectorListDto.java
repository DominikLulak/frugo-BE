package com.lulak.frugo.dto.warehouse;

public class AdminWarehouseSectorListDto {

    private Integer id;
    private String code;
    private String name;
    private Integer sectorTypeId;
    private String typeCode;
    private String typeName;
    private String description;

    public AdminWarehouseSectorListDto(
            Integer id,
            String code,
            String name,
            Integer sectorTypeId,
            String typeCode,
            String typeName,
            String description
    ){
        this.id = id;
        this.code = code;
        this.name = name;
        this.sectorTypeId = sectorTypeId;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.description = description;
    }

    public Integer getId(){ return id; }
    public String getCode(){ return code; }
    public String getName(){ return name; }
    public Integer getSectorTypeId(){ return sectorTypeId; }
    public String getTypeCode(){ return typeCode; }
    public String getTypeName(){ return typeName; }
    public String getDescription(){ return description; }
}
