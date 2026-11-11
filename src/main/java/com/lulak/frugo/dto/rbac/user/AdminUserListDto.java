package com.lulak.frugo.dto.rbac.user;

public class AdminUserListDto {

    private final Integer id;
    private final String employeeNumber;
    private final String loginName;
    private final String fullName;
    private final String departmentName;
    private final String jobPositionName;

    public AdminUserListDto(
            Integer id,
            String employeeNumber,
            String loginName,
            String fullName,
            String departmentName,
            String jobPositionName
    ){
        this.id = id;
        this.employeeNumber = employeeNumber;
        this.loginName = loginName;
        this.fullName = fullName;
        this.departmentName = departmentName;
        this.jobPositionName = jobPositionName;
    }

    public Integer getId(){ return id; }
    public String getEmployeeNumber(){ return employeeNumber; }
    public String getLoginName(){ return loginName; }
    public String getFullName(){ return fullName; }
    public String getDepartmentName(){ return departmentName; }
    public String getJobPositionName(){ return jobPositionName; }
}
