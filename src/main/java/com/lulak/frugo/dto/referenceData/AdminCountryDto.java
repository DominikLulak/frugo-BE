package com.lulak.frugo.dto.referenceData;

public class AdminCountryDto {

    private Integer id;
    private String code;
    private String name;

    public AdminCountryDto(
            Integer id,
            String code,
            String name
    ){
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Integer getId(){ return id; }
    public String getCode(){ return code; }
    public String getName(){ return name; }
}
