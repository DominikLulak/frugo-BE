package com.lulak.frugo.dto.purchaseOrders;

public class AdminSupplierListDto {

    private Integer id;
    private String name;
    private String internalCode;
    private boolean active;

    public AdminSupplierListDto(
            Integer id,
            String name,
            String internalCode,
            boolean active
    ){
        this.id = id;
        this.name = name;
        this.internalCode = internalCode;
        this.active = active;
    }

    public Integer getId(){ return id; }
    public String getName(){ return name; }
    public String getInternalCode(){ return internalCode; }
    public boolean isActive(){ return active; }
}
