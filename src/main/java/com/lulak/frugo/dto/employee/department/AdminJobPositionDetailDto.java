package com.lulak.frugo.dto.employee.department;

import com.lulak.frugo.dto.employee.AdminEmployeeListDto;

import java.util.List;

public class AdminJobPositionDetailDto {

    private Integer id;
    private String departmentName;
    private String code;
    private String name;
    private String description;
    private List<AdminEmployeeListDto> employees;

    public AdminJobPositionDetailDto(
            Integer id,
            String departmentName,
            String code,
            String name,
            String description,
            List<AdminEmployeeListDto> employees
    ){
        this.id = id;
        this.departmentName = departmentName;
        this.code = code;
        this.name = name;
        this.description = description;
        this.employees = employees;
    }

    public Integer getId(){ return id; }
    public String getDepartmentName(){ return departmentName; }
    public String getCode(){ return code; }
    public String getName(){ return name; }
    public String getDescription(){ return description; }
    public List<AdminEmployeeListDto> getEmployees(){ return employees; }
}
