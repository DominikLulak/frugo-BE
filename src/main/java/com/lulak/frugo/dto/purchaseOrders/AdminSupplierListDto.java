package com.lulak.frugo.dto.purchaseOrders;

public class AdminSupplierListDto {

    private Integer id;
    private String name;
    private String internalCode;

    public AdminSupplierListDto(
            Integer id,
            String name,
            String internalCode
    ){
        this.id = id;
        this.name = name;
        this.internalCode = internalCode;
    }

    public Integer getId(){ return id; }
    public String getName(){ return name; }
    public String getInternalCode(){ return internalCode; }
}
