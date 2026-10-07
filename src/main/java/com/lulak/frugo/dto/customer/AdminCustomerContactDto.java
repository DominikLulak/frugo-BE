package com.lulak.frugo.dto.customer;

public class AdminCustomerContactDto {

    private Integer id;
    private String name;
    private String phoneNumber;
    private String email;
    private boolean primary;

    public AdminCustomerContactDto(
            Integer id,
            String name,
            String phoneNumber,
            String email,
            boolean primary
    ){
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.primary = primary;
    }

    public Integer getId(){ return id; }
    public String getName(){ return name; }
    public String getPhoneNumber(){ return phoneNumber; }
    public String getEmail(){ return email; }
    public boolean isPrimary(){ return primary; }
}
