package com.lulak.frugo.dto.rbac.CRUD;

import javax.validation.constraints.NotBlank;

public class RoleCreateDto {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
}
