package com.lulak.frugo.dto.customer.CRUD;

import javax.validation.constraints.NotBlank;

public class CustomerContactCrudDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String email;

    private boolean primary;

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getPhoneNumber(){ return phoneNumber; }
    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public boolean isPrimary(){ return primary; }
    public void setPrimary(boolean primary){ this.primary = primary; }
}
