package com.lulak.frugo.model.customer;

import com.lulak.frugo.model.Country;
import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "company_id", unique = true)
    private String companyId;

    @Column(nullable = false)
    private String street;

    @Column(name = "house_number", nullable = false)
    private String houseNumber;

    @Column(nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "is_registered", nullable = false)
    private boolean registered;

    public Customer(){ }

    public Integer getId(){ return id; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getCompanyId(){ return companyId; }
    public void setCompanyId(String companyId){ this.companyId = companyId; }

    public String getStreet(){ return street; }
    public void setStreet(String street ){ this.street = street; }

    public String getHouseNumber(){ return houseNumber; }
    public void setHouseNumber(String houseNumber){ this.houseNumber = houseNumber; }

    public String getCity(){ return city; }
    public void setCity(String city){ this.city = city; }

    public String getPostalCode(){ return postalCode; }
    public void setPostalCode(String postalCode){ this.postalCode = postalCode; }

    public Country getCountry(){ return country; }
    public void setCountry(Country country){ this.country = country; }

    public boolean isRegistered(){ return registered; }
    public void setRegistered(boolean registered){ this.registered = registered; }
}
