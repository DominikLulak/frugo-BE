package com.lulak.frugo.dto.employee;

public class AdminEmployeeListDto {

    public String employeeNumber;
    public String firstName;
    public String lastName;
    public String jobPosition;
    public String phone;
    public String shift;

    public AdminEmployeeListDto(String employeeNumber, String firstName, String lastName,
                                  String position, String phoneNumber, String shift){
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobPosition = position;
        this.phone = phoneNumber;
        this.shift = shift;
    }
}
