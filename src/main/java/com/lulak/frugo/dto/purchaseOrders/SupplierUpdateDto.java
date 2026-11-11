package com.lulak.frugo.dto.purchaseOrders;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SupplierUpdateDto {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String internalCode;

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getInternalCode(){ return internalCode; }
    public void setInternalCode(String internalCode){ this.internalCode = internalCode; }
}
