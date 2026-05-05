package com.lulak.frugo.dto;

public class AdminOrderDetailDto {

    public String category;
    public String name;
    public String variant;
    public int quantity;

    public AdminOrderDetailDto(String category, String name, String variant, int quantity){
        this.category = category;
        this.name = name;
        this.variant = variant;
        this.quantity = quantity;
    }
}
