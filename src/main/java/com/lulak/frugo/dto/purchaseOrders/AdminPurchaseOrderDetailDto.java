package com.lulak.frugo.dto.purchaseOrders;

import java.time.LocalDateTime;
import java.util.List;

public class AdminPurchaseOrderDetailDto {

    private Integer id;
    private String purchaseOrderNumber;
    private Integer supplierId;
    private String supplierName;
    private String supplierInternalCode;
    private LocalDateTime createdAt;
    private String employeeName;
    private String statusCode;
    private List<AdminPurchaseOrderItemDto> items;

    public AdminPurchaseOrderDetailDto(
            Integer id,
            String purchaseOrderNumber,
            Integer supplierId,
            String supplierName,
            String supplierInternalCode,
            LocalDateTime createdAt,
            String employeeName,
            String statusCode,
            List<AdminPurchaseOrderItemDto> items
    ){
        this.id = id;
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierInternalCode = supplierInternalCode;
        this.createdAt = createdAt;
        this.employeeName = employeeName;
        this.statusCode = statusCode;
        this.items = items;
    }

    public Integer getId(){ return id; }
    public String getPurchaseOrderNumber(){ return purchaseOrderNumber; }
    public Integer getSupplierId(){ return supplierId; }
    public String getSupplierName(){ return supplierName; }
    public String getSupplierInternalCode(){ return supplierInternalCode; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public String getEmployeeName(){ return employeeName; }
    public String getStatusCode(){ return statusCode; }
    public List<AdminPurchaseOrderItemDto> getItems(){ return items; }
}