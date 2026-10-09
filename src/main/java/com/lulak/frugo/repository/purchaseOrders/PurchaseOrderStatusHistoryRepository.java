package com.lulak.frugo.repository.purchaseOrders;

import com.lulak.frugo.dto.event.PurchaseOrderStatusHistoryListDto;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseOrderStatusHistoryRepository extends JpaRepository<PurchaseOrderStatusHistory, Integer> {

    List<PurchaseOrderStatusHistory> findByPurchaseOrderIdOrderByChangedAtAsc(
            Integer purchaseOrderId
    );

    @Query("""
        SELECT new com.lulak.frugo.dto.event.PurchaseOrderStatusHistoryListDto(
            sh.id,
            po.purchaseOrderNumber,
            os.code,
            ss.code,
            sh.changedAt,
            CONCAT(e.firstName, ' ', e.lastName),
            sh.note
        )
        FROM PurchaseOrderStatusHistory sh
        JOIN sh.purchaseOrder po
        LEFT JOIN sh.oldStatus os
        JOIN sh.newStatus ss
        LEFT JOIN sh.employee e
        WHERE(
            COALESCE(:purchaseOrderNumber, '') = ''
            OR po.purchaseOrderNumber LIKE CONCAT('%', :purchaseOrderNumber, '%') 
        )
        AND(
            COALESCE(:employeeName, '') = ''
            OR LOWER(CONCAT(e.firstName, ' ', e.lastName))
                LIKE LOWER(CONCAT('%', :employeeName, '%'))
        )
        ORDER BY sh.id, sh.changedAt
    """)
    List<PurchaseOrderStatusHistoryListDto> getFilteredStatusHistories(
            @Param("purchaseOrderNumber") String purchaseOrderNumber,
            @Param("employeeName") String employeeName
    );
}
