package com.lulak.frugo.dto.event;

import java.time.LocalDateTime;

public class PurchaseOrderStatusHistoryListDto {

    private Integer id;

    private String purchaseOrderNumber;

    private String oldStatusCode;
    private String newStatusCode;

    private LocalDateTime changedAt;

    private String employeeName;
    private String note;

    public PurchaseOrderStatusHistoryListDto(
            Integer id,
            String purchaseOrderNumber,
            String oldStatusCode,
            String newStatusCode,
            LocalDateTime changedAt,
            String employeeName,
            String note
    ){
        this.id = id;
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.oldStatusCode = oldStatusCode;
        this.newStatusCode = newStatusCode;
        this.changedAt = changedAt;
        this.employeeName = employeeName;
        this.note = note;
    }

    public Integer getId(){ return id; }
    public String getPurchaseOrderNumber(){ return purchaseOrderNumber; }
    public String getOldStatusCode(){ return oldStatusCode; }
    public String getNewStatusCode(){ return newStatusCode; }
    public LocalDateTime getChangedAt(){ return changedAt; }
    public String getEmployeeName(){ return employeeName; }
    public String getNote(){ return note; }
}
