package com.lulak.frugo.dto.employee;

import java.time.LocalDate;

public class AdminEmployeeDetailDto {

    private String employeeNumber;
    private String name;
    private String address;
    private String city;
    private String postalCode;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private String phone;
    private String email;
    private String systemUsername;
    private String shiftCode;
    private String departmentName;
    private String jobPositionName;
    private boolean active;
    private LocalDate terminationDate;
    private String loginUsername;

    public AdminEmployeeDetailDto(
            String employeeNumber,
            String name,
            String address,
            String city,
            String postalCode,
            LocalDate birthDate,
            LocalDate hireDate,
            String phone,
            String email,
            String systemUsername,
            String shiftCode,
            String departmentName,
            String jobPositionName,
            boolean active,
            LocalDate terminationDate,
            String loginUsername
    ){
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.phone = phone;
        this.email = email;
        this.systemUsername = systemUsername;
        this.shiftCode = shiftCode;
        this.departmentName = departmentName;
        this.jobPositionName = jobPositionName;
        this.active = active;
        this.terminationDate = terminationDate;
        this.loginUsername = loginUsername;
    }

    public String getEmployeeNumber(){ return employeeNumber; }
    public String getName(){ return name; }
    public String getAddress(){ return address; }
    public String getCity(){ return city; }
    public String getPostalCode(){ return postalCode; }
    public LocalDate getBirthDate(){ return birthDate; }
    public LocalDate getHireDate(){ return hireDate; }
    public String getPhone(){ return phone; }
    public String getEmail(){ return email; }
    public String getSystemUsername(){ return systemUsername; }
    public String getShiftCode(){ return shiftCode; }
    public String getDepartmentName(){ return departmentName; }
    public String getJobPositionName(){ return jobPositionName; }
    public boolean isActive(){ return active; }
    public LocalDate getTerminationDate(){ return terminationDate; }
    public String getLoginUsername(){ return loginUsername; }
}
