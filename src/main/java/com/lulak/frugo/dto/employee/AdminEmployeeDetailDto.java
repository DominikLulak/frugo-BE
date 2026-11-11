package com.lulak.frugo.dto.employee;

import java.time.LocalDate;

public class AdminEmployeeDetailDto {

    private Integer id;
    private String employeeNumber;
    private String name;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String postalCode;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private String phone;
    private String email;
    private String systemUsername;
    private Integer shiftId;
    private String shiftCode;
    private Integer departmentId;
    private String departmentName;
    private Integer jobPositionId;
    private String jobPositionName;
    private boolean active;
    private LocalDate terminationDate;
    private String loginUsername;

    public AdminEmployeeDetailDto(
            Integer id,
            String employeeNumber,
            String name,
            String firstName,
            String lastName,
            String address,
            String city,
            String postalCode,
            LocalDate birthDate,
            LocalDate hireDate,
            String phone,
            String email,
            String systemUsername,
            Integer shiftId,
            String shiftCode,
            Integer departmentId,
            String departmentName,
            Integer jobPositionId,
            String jobPositionName,
            boolean active,
            LocalDate terminationDate,
            String loginUsername
    ){
        this.id = id;
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.phone = phone;
        this.email = email;
        this.systemUsername = systemUsername;
        this.shiftId = shiftId;
        this.shiftCode = shiftCode;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.jobPositionId = jobPositionId;
        this.jobPositionName = jobPositionName;
        this.active = active;
        this.terminationDate = terminationDate;
        this.loginUsername = loginUsername;
    }

    public Integer getId(){ return id; }
    public String getEmployeeNumber(){ return employeeNumber; }
    public String getName(){ return name; }
    public String getFirstName(){ return firstName; }
    public String getLastName(){ return lastName; }
    public String getAddress(){ return address; }
    public String getCity(){ return city; }
    public String getPostalCode(){ return postalCode; }
    public LocalDate getBirthDate(){ return birthDate; }
    public LocalDate getHireDate(){ return hireDate; }
    public String getPhone(){ return phone; }
    public String getEmail(){ return email; }
    public String getSystemUsername(){ return systemUsername; }
    public Integer getShiftId(){ return shiftId; }
    public String getShiftCode(){ return shiftCode; }
    public Integer getDepartmentId(){ return departmentId; }
    public String getDepartmentName(){ return departmentName; }
    public Integer getJobPositionId(){ return jobPositionId; }
    public String getJobPositionName(){ return jobPositionName; }
    public boolean isActive(){ return active; }
    public LocalDate getTerminationDate(){ return terminationDate; }
    public String getLoginUsername(){ return loginUsername; }
}
