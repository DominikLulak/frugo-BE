package com.lulak.frugo.dto.referenceData.CRUD;

import javax.validation.constraints.NotBlank;

public class ShiftCreateDto {

    @NotBlank
    private String code;

    private String description;

    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code;}

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }
}
