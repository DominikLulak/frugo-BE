package com.lulak.frugo.dto.customer;

import java.util.List;

public class AdminCustomerDetailDto {

    private Integer id;
    private String name;
    private String companyId;
    private String countryCode;
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;
    private boolean registered;
    private List<AdminCustomerContactDto> contacts;

    public AdminCustomerDetailDto(
            Integer id,
            String name,
            String companyId,
            String countryCode,
            String city,
            String postalCode,
            String street,
            String houseNumber,
            boolean registered,
            List<AdminCustomerContactDto> contacts
    ){
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.countryCode = countryCode;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.registered = registered;
        this.contacts = contacts;
    }

    public Integer getId(){ return id; }
    public String getName(){ return name; }
    public String getCompanyId(){ return companyId; }
    public String getCountryCode(){ return countryCode; }
    public String getCity(){ return city; }
    public String getPostalCode(){ return postalCode; }
    public String getStreet(){ return street; }
    public String getHouseNumber(){ return houseNumber; }
    public boolean isRegistered(){ return registered; }
    public List<AdminCustomerContactDto> getContacts(){ return contacts; }
}
