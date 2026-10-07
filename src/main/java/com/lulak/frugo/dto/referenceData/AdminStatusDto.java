package com.lulak.frugo.dto.referenceData;

public class AdminStatusDto {

    private Integer id;
    private String code;
    private String name;
    private String description;

    public AdminStatusDto(
            Integer id,
            String code,
            String name,
            String description
    ){
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Integer getId(){ return id; }
    public String getCode(){ return code; }
    public String getName(){ return name; }
    public String getDescription(){ return description; }
}
