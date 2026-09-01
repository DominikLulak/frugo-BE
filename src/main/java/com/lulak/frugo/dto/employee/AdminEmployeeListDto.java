package com.lulak.frugo.dto.employee;

public class AdminEmployeeListDto {

    private Integer id;
    private String employeeNumber;
    private String name;
    private String shiftCode;
    private String departmentName;
    private String jobPositionName;
    private boolean active;

    public AdminEmployeeListDto(
            Integer id,
            String employeeNumber,
            String name,
            String shiftCode,
            String departmentName,
            String jobPositionName,
            boolean active
    ){
        this.id = id;
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.shiftCode = shiftCode;
        this.departmentName = departmentName;
        this.jobPositionName = jobPositionName;
        this.active = active;
    }

    public Integer getId(){ return id; }
    public String getEmployeeNumber(){ return employeeNumber; }
    public String getName(){ return name; }
    public String getShiftCode(){ return shiftCode; }
    public String getDepartmentName(){ return departmentName; }
    public String getJobPositionName(){ return jobPositionName; }
    public boolean isActive(){ return active; }
}
