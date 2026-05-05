package com.lulak.frugo.dto;

public class AdminCustomerDetailDto {

    public String customerNumber;
    public String name;
    public String country;
    public String address;
    public String executive;
    public String email;
    public String phoneNumber;

    public AdminCustomerDetailDto(String customerNumber, String name,
                                  String country, String address, String executive,
                                  String email, String phoneNumber){
        this.customerNumber = customerNumber;
        this.name = name;
        this.country = country;
        this.address = address;
        this.executive = executive;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
