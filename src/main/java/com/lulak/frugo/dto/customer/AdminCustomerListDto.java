package com.lulak.frugo.dto.customer;

public class AdminCustomerListDto {

    public Integer id;
    public String name;
    public String companyId;
    public String city;
    public String postalCode;
    public boolean registered;

    public AdminCustomerListDto(
            Integer id,
            String name,
            String companyId,
            String city,
            String postalCode,
            boolean registered
    ){
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.city = city;
        this.postalCode = postalCode;
        this.registered = registered;
    }
}
