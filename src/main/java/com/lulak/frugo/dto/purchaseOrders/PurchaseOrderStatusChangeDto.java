package com.lulak.frugo.dto.purchaseOrders;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PurchaseOrderStatusChangeDto {

    @NotBlank(message = "New status is required")
    private String statusCode;

    @NotNull(message = "Note is required")
    @NotBlank(message = "Note cannot be blank")
    private String note;

    public String getStatusCode(){ return statusCode; }
    public void setStatusCode(String statusCode){ this.statusCode = statusCode; }

    public String getNote(){ return note; }
    public void setNote(String note){ this.note = note; }
}
