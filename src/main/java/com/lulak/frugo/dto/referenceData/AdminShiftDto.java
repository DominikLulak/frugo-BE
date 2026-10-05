package com.lulak.frugo.dto.referenceData;

public class AdminShiftDto {

    private Integer id;
    private String code;
    private String description;

    public AdminShiftDto(
            Integer id,
            String code,
            String description
    ){
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public Integer getId(){ return id; }
    public String getCode(){return code; }
    public String getDescription(){ return description; }
}
