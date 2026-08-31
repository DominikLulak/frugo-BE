package com.lulak.frugo.repository.order;

import com.lulak.frugo.dto.order.AdminOrderListDto;
import com.lulak.frugo.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.order.AdminOrderListDto(
            o.id,
            o.orderNumber,
            o.customer.name,
            o.createdAt,
            o.status.code
        )
        FROM Order o
        WHERE
            (
                COALESCE(:orderNumber, '') = ''
                OR o.orderNumber LIKE CONCAT('%', COALESCE(:orderNumber, ''), '%')
            )
    
        AND (
            COALESCE(:customerName, '') = ''
            OR LOWER(o.customer.name)
                LIKE LOWER(CONCAT('%', COALESCE(:customerName, ''), '%'))
        )
    
        AND (
            COALESCE(:statusCode, '') = ''
            OR o.status.code = COALESCE(:statusCode, '')
        )
    """)
    List<AdminOrderListDto> getFilteredOrders(
            @Param("orderNumber") String orderNumber,
            @Param("customerName") String customerName,
            @Param("statusCode") String statusCode

    );
}
