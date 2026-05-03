package com.lulak.frugo.dto;

public class AdminStockItemDto {

    public String category;
    public String name;
    public String variant;

    public AdminStockItemDto(
            String category,
            String name,
            String variants
    ){
        this.category = category;
        this.name = name;
        this.variant = variants;
    }

    public String getCategory(){
        return category;
    }
    public String getName(){
        return name;
    }
    public String getVariant() {
        return variant;
    }
}
