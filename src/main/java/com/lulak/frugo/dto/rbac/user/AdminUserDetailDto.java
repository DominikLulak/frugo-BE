package com.lulak.frugo.dto.rbac.user;

import com.lulak.frugo.dto.rbac.role.AdminRoleListDto;

import java.util.List;

public class AdminUserDetailDto {

    private Integer id;
    private String employeeNumber;
    private String fullName;
    private String loginName;
    private String systemUsername;
    private String departmentName;
    private String jobPositionName;
    private List<AdminRoleListDto> roles;

    public AdminUserDetailDto(
            Integer id,
            String employeeNumber,
            String fullName,
            String loginName,
            String systemUsername,
            String departmentName,
            String jobPositionName,
            List<AdminRoleListDto> roles
    ){
        this.id = id;
        this.employeeNumber = employeeNumber;
        this.fullName = fullName;
        this.loginName = loginName;
        this.systemUsername = systemUsername;
        this.departmentName = departmentName;
        this.jobPositionName = jobPositionName;
        this.roles = roles;
    }

    public Integer getId(){ return id; }
    public String getEmployeeNumber(){ return employeeNumber; }
    public String getFullName(){ return fullName; }
    public String getLoginName(){ return loginName; }
    public String getSystemUsername(){ return systemUsername; }
    public String getDepartmentName(){ return departmentName; }
    public String getJobPositionName(){ return jobPositionName; }
    public List<AdminRoleListDto> getRoles(){ return roles; }
}
