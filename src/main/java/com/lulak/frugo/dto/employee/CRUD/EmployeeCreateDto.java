package com.lulak.frugo.dto.employee.CRUD;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class EmployeeCreateDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotBlank
    private String address;
    @NotBlank
    private String city;
    @NotBlank
    private String postalCode;

    @NotBlank
    private LocalDate birthDate;

    @NotBlank
    private Integer shiftId;
    @NotBlank
    private Integer jobPositionId;

    @NotBlank
    private String systemUsername;

    @NotBlank
    private String phone;
    @NotBlank
    private String email;


    public String getFirstName(){ return firstName; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public String getLastName(){ return lastName; }
    public void setLastName(String lastName){ this.lastName = lastName; }

    public String getAddress(){ return address; }
    public void setAddress(String address){ this.address = address; }

    public String getCity(){ return city; }
    public void setCity(String city){ this.city = city; }

    public String getPostalCode(){ return postalCode; }
    public void setPostalCode(String postalCode){ this.postalCode = postalCode; }

    public LocalDate getBirthDate(){ return birthDate; }
    public void setBirthDate(LocalDate birthDate){this.birthDate = birthDate; }

    public Integer getShiftId(){ return shiftId; }
    public void setShiftId(Integer shiftId){ this.shiftId = shiftId; }

    public Integer getJobPositionId(){ return jobPositionId; }
    public void setJobPositionId(Integer jobPositionId){ this.jobPositionId = jobPositionId; }

    public String getSystemUsername(){ return systemUsername; }
    public void setSystemUsername(String systemUsername){ this.systemUsername = systemUsername; }

    public String getPhone(){ return phone; }
    public void setPhone(String phone){ this.phone = phone; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }
}
