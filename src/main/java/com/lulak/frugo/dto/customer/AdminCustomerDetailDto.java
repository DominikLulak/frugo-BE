package com.lulak.frugo.dto.customer;

public class AdminCustomerDetailDto {

    public Integer id;
    public String name;
    public String companyId;
    public String street;
    public String houseNumber;
    public String city;
    public String postalCode;
    public String countryCode;
    public String countryName;
    public boolean registered;

    public AdminCustomerDetailDto(
            Integer id,
            String name,
            String companyId,
            String street,
            String houseNumber,
            String city,
            String postalCode,
            String countryCode,
            String countryName,
            boolean registered
    ){
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.registered = registered;
    }
}
