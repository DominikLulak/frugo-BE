package com.lulak.frugo.dto;

import java.time.LocalDate;

public class AdminEmployeeDetailDto {

    public String personalNumber;
    public String firstName;
    public String lastName;
    public String position;
    public String phoneNumber;
    public String email;
    public String address;
    public String shift;
    public LocalDate hireDate;
    public LocalDate birthDate;

    public AdminEmployeeDetailDto(String personalNumber, String firstName, String lastName,
                                  String position, String phoneNumber, String email,
                                  String address, String shift, LocalDate hireDate, LocalDate birthDate){
        this.personalNumber = personalNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.shift = shift;
        this.hireDate = hireDate;
        this.birthDate = birthDate;
    }
}
