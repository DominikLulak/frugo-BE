package com.lulak.frugo.dto.purchaseOrders;

import javax.validation.constraints.NotBlank;

public class PurchaseOrderStatusUpdateDto {

    @NotBlank
    private String statusCode;

    @NotBlank
    private String note;

    public String getStatusCode(){ return statusCode; }
    public void setStatusCode(String statusCode){ this.statusCode = statusCode; }

    public String getNote(){ return note; }
    public void setNote(String note){ this.note = note; }
}
