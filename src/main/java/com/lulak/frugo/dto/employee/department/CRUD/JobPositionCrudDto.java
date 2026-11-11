package com.lulak.frugo.dto.employee.department.CRUD;

import javax.validation.constraints.NotBlank;

public class JobPositionCrudDto {

    @NotBlank
    private Integer departmentId;

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    private String description;

    public Integer getDepartmentId(){ return departmentId; }
    public void setDepartmentId(Integer departmentId){ this.departmentId = departmentId; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code; }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }
}
