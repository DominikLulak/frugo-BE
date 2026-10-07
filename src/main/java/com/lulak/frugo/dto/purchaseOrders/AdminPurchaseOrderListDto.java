package com.lulak.frugo.dto.purchaseOrders;

import java.time.LocalDateTime;

public class AdminPurchaseOrderListDto {

    private Integer id;
    private String purchaseOrderNumber;
    private String supplierName;
    private LocalDateTime createdAt;
    private String employeeName;
    private String statusCode;

    public AdminPurchaseOrderListDto(
            Integer id,
            String purchaseOrderNumber,
            String supplierName,
            LocalDateTime createdAt,
            String employeeName,
            String statusCode
    ){
        this.id = id;
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.supplierName = supplierName;
        this.createdAt = createdAt;
        this.employeeName = employeeName;
        this.statusCode = statusCode;
    }

    public Integer getId(){ return id; }
    public String getPurchaseOrderNumber(){ return purchaseOrderNumber; }
    public String getSupplierName(){ return supplierName; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public String getEmployeeName(){ return employeeName; }
    public String getStatusCode(){ return statusCode; }
}
