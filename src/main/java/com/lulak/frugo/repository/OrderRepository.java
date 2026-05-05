package com.lulak.frugo.repository;

import com.lulak.frugo.model.Order;
import com.lulak.frugo.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT o FROM Order o
        WHERE (:orderNumber IS NULL OR o.orderNumber LIKE %:orderNumber%)
        AND (:status IS NULL OR o.status = :status)
        AND (:customerName IS NULL OR LOWER(o.customerName) LIKE LOWER(CONCAT('%', :customerName, '%')))
    """)
    List<Order> getFilteredOrders(
            String orderNumber,
            Status status,
            String customerName
    );
}
