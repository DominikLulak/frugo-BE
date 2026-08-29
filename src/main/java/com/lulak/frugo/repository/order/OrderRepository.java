package com.lulak.frugo.repository.order;

import com.lulak.frugo.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("""
        SELECT o FROM Order o
        WHERE (:orderNumber IS NULL OR o.orderNumber LIKE CONCAT('%', :orderNumber, '%'))
        AND (:status IS NULL OR o.status.code = :status)
        AND (:customerName IS NULL OR LOWER(o.customer.name) LIKE LOWER(CONCAT('%', :customerName, '%')))
    """)
    List<Order> getFilteredOrders(
            String orderNumber,
            String status,
            String customerName
    );
}
