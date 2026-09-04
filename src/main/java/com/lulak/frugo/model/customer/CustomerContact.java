package com.lulak.frugo.model.customer;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_contact")
public class CustomerContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(name = "is_primary", nullable = false)
    private boolean primary;

    public Integer getId(){ return id; }

    public Customer getCustomer(){ return customer; }
    public void setCustomer(Customer customer){ this.customer = customer; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getPhoneNumber(){ return phoneNumber; }
    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public boolean isPrimary(){ return primary; }
    public void setPrimary(boolean primary){ this.primary = primary; }

}
