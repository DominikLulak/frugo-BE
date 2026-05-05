package com.lulak.frugo.dto;

public class AdminCustomerListDto {

    public String customerNumber;
    public String name;
    public String email;
    public String phoneNumber;

    public AdminCustomerListDto(String customerNumber, String name,
                                String email, String phoneNumber){
        this.customerNumber = customerNumber;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
