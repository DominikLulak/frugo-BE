package com.lulak.frugo.repository;

import com.lulak.frugo.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderOrderNumber(String orderNumber);
}
