package com.lulak.frugo.dto.employee.department;

public class AdminJobPositionListDto {

    private Integer id;
    private String departmentName;
    private String code;
    private String name;
    private String description;

    public AdminJobPositionListDto(
            Integer id,
            String departmentName,
            String code,
            String name,
            String description
    ){
        this.id = id;
        this.departmentName = departmentName;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Integer getId(){ return id; }
    public String getDepartmentName(){ return departmentName; }
    public String getCode(){ return code; }
    public String getName(){ return name; }
    public String getDescription(){ return description; }
}
