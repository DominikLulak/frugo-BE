package com.lulak.frugo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String customerNumber;

    private String name;
    private String country;
    private String address;
    private String executive;
    private String email;
    private String phoneNumber;

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getCustomerNumber(){return customerNumber;}
    public void setCustomerNumber(String customerNumber){this.customerNumber = customerNumber;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getCountry(){return country;}
    public void setCountry(String country){this.country = country;}

    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}

    public String getExecutive(){return executive;}
    public void setExecutive(String executive){this.executive = executive;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getPhoneNumber(){return phoneNumber;}
    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}
}
