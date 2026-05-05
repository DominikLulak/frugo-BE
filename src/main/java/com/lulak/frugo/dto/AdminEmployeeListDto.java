package com.lulak.frugo.dto;

import java.time.LocalDate;

public class AdminEmployeeListDto {

    public String personalNumber;
    public String firstName;
    public String lastName;
    public String position;
    public String phoneNumber;
    public String shift;

    public AdminEmployeeListDto(String personalNumber, String firstName, String lastName,
                                  String position, String phoneNumber, String shift){
        this.personalNumber = personalNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.shift = shift;
    }
}
