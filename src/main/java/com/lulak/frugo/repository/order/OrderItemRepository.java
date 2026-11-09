package com.lulak.frugo.repository.order;

import com.lulak.frugo.dto.order.AdminOrderItemDetailDto;
import com.lulak.frugo.model.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.order.AdminOrderItemDetailDto(
            oi.id,
            wi.product.id,
            wi.id,
            oi.order.orderNumber,
            wi.product.productType.category.code,
            wi.product.productType.name,
            wi.product.name,
            wi.etiNumber,
            oi.quantity,
            oi.pickedQuantity,
            oi.status.code
        )
        FROM OrderItem oi
        JOIN oi.warehouseItem wi
        WHERE oi.order.id = :orderId
        ORDER BY oi.id
    """)
    List<AdminOrderItemDetailDto> getOrderItems(
            @Param("orderId") Integer orderId
    );

    Optional<OrderItem> findByIdAndOrderId(
            Integer itemId,
            Integer orderId
    );

    void deleteByOrderId(Integer orderId);

    boolean existsByOrderIdAndPickedQuantityGreaterThan(
            Integer orderId,
            Integer pickedQuantity
    );

    boolean existsByOrderIdAndWarehouseItemProductId(
            Integer orderId,
            Integer productId
    );

    List<OrderItem> findAllByOrderIdAndWarehouseItemProductId(
            Integer orderId,
            Integer productId
    );

    long countByOrderId(Integer orderId);

    List<OrderItem> findAllByOrderId(Integer orderId);
}
