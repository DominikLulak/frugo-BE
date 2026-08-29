package com.lulak.frugo.dto.employee;

import java.time.LocalDate;

public class AdminEmployeeDetailDto {

    public String employeeNumber;
    public String firstName;
    public String lastName;
    public String jobPosition;
    public String phone;
    public String email;
    public String address;
    public String shift;
    public LocalDate hireDate;
    public LocalDate birthDate;

    public AdminEmployeeDetailDto(String employeeNumber, String firstName, String lastName,
                                  String position, String phoneNumber, String email,
                                  String address, String shift, LocalDate hireDate, LocalDate birthDate){
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobPosition = position;
        this.phone = phoneNumber;
        this.email = email;
        this.address = address;
        this.shift = shift;
        this.hireDate = hireDate;
        this.birthDate = birthDate;
    }
}
