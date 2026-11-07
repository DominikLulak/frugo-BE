package com.lulak.frugo.dto.product;

public class ProductTypeDto {

    private final Integer id;
    private final String name;

    public ProductTypeDto(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
