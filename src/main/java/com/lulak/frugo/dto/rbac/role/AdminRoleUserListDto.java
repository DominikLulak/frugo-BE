package com.lulak.frugo.dto.rbac.role;

public class AdminRoleUserListDto {

    private Integer id;
    private String employeeNumber;
    private String fullName;
    private String departmentName;
    private String jobPositionName;

    public AdminRoleUserListDto(
            Integer id,
            String employeeNumber,
            String fullName,
            String departmentName,
            String jobPositionName
    ){
        this.id = id;
        this.employeeNumber = employeeNumber;
        this.fullName = fullName;
        this.departmentName = departmentName;
        this.jobPositionName = jobPositionName;
    }

    public Integer getId(){ return id; }
    public String getFullName(){ return fullName; }
    public String getEmployeeNumber(){ return employeeNumber; }
    public String getDepartmentName(){ return departmentName; }
    public String getJobPositionName(){ return jobPositionName; }
}
