package com.lulak.frugo.dto.customer;

public class AdminCustomerListDto {

    private Integer id;
    private String name;
    private String companyId;
    private Integer countryId;
    private String countryCode;
    private String city;
    private String postalCode;
    private boolean registered;

    public AdminCustomerListDto(
            Integer id,
            String name,
            String companyId,
            Integer countryId,
            String countryCode,
            String city,
            String postalCode,
            boolean registered
    ){
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.countryId = countryId;
        this.countryCode = countryCode;
        this.city = city;
        this.postalCode = postalCode;
        this.registered = registered;
    }

    public Integer getId(){ return id; }
    public String getName(){ return name; }
    public String getCompanyId(){ return companyId; }
    public Integer getCountryId(){ return countryId; }
    public String getCountryCode(){ return countryCode; }
    public String getCity(){ return city; }
    public String getPostalCode(){ return postalCode; }
    public boolean isRegistered(){ return registered; }
}
