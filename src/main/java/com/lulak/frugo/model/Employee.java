package com.lulak.frugo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String personalNumber;

    private String firstName;
    private String lastName;
    private String position;
    private String phoneNumber;
    private String email;
    private String address;
    private String shift;
    private LocalDate hireDate;
    private LocalDate birthDate;

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getPersonalNumber(){return personalNumber;}
    public void setPersonalNumber(String personalNumber){this.personalNumber = personalNumber;}

    public String getFirstName(){return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getLastName(){return lastName;}
    public void setLastName(String lastName){this.lastName = lastName;}

    public String getPosition(){return position;}
    public void setPosition(String position){this.position = position;}

    public String getPhoneNumber(){return phoneNumber;}
    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}

    public String getShift(){return shift;}
    public void setShift(String shift){this.shift = shift;}

    public LocalDate getHireDate(){return hireDate;}
    public void setHireDate(LocalDate hireDate){this.hireDate = hireDate;}

    public LocalDate getBirthDate(){return birthDate;}
    public void setBirthDate(LocalDate birthDate){this.birthDate = birthDate;}

}
