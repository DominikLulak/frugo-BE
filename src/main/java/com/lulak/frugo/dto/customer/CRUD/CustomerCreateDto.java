package com.lulak.frugo.dto.customer.CRUD;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CustomerCreateDto {

    @NotBlank
    private String name;

    private String companyId;

    @NotBlank
    private String street;

    @NotBlank
    private String houseNumber;

    @NotBlank
    private String city;

    @NotBlank
    private String postalCode;

    @NotNull
    private Integer countryId;

    private boolean registered;

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getCompanyId(){ return companyId; }
    public void setCompanyId(String companyId){ this.companyId = companyId; }

    public String getStreet(){ return street; }
    public void setStreet(String street){ this.street = street; }

    public String getHouseNumber(){ return houseNumber; }
    public void setHouseNumber(String houseNumber){ this.houseNumber = houseNumber; }

    public String getCity(){ return city; }
    public void setCity(String city){ this.city = city; }

    public String getPostalCode(){ return postalCode; }
    public void setPostalCode(String postalCode){ this.postalCode = postalCode; }

    public Integer getCountryId(){ return countryId; }
    public void setCountryId(Integer countryId){ this.countryId = countryId; }

    public boolean isRegistered(){ return registered; }
    public void setRegistered(boolean registered){ this.registered = registered; }
}
