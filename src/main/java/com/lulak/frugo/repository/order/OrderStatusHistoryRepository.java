package com.lulak.frugo.repository.order;

import com.lulak.frugo.dto.event.OrderStatusHistoryListDto;
import com.lulak.frugo.model.order.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.event.OrderStatusHistoryListDto(
            sh.id,
            o.orderNumber,
            os.code,
            ns.code,
            sh.changedAt,
            CONCAT(e.firstName, ' ', e.lastName),
            sh.note 
        )
        FROM OrderStatusHistory sh
        JOIN sh.order o
        LEFT JOIN sh.oldStatus os
        JOIN sh.newStatus ns
        LEFT JOIN sh.employee e
        WHERE(
            COALESCE(:orderNumber, '') = ''
            OR o.orderNumber LIKE CONCAT('%', :orderNumber, '%') 
        )
        AND (
            COALESCE(:employeeName, '') = ''
            OR LOWER(CONCAT(e.firstName, ' ', e.lastName))
                LIKE LOWER(CONCAT('%', :employeeName, '%')) 
        )
        ORDER BY sh.id, sh.changedAt
    """)
    List<OrderStatusHistoryListDto> getFilteredStatusHistories(
            @Param("orderNumber") String orderNumber,
            @Param("employeeName") String employeeName
    );
}
