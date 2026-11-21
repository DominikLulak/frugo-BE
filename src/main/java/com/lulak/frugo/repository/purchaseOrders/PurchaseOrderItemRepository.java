package com.lulak.frugo.repository.purchaseOrders;

import com.lulak.frugo.dto.purchaseOrders.list.AdminPurchaseOrderItemDto;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrderItem;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.purchaseOrders.list.AdminPurchaseOrderItemDto(
            poi.id,
            poi.product.productType.category.code,
            poi.product.productType.name,
            poi.product.name,
            poi.country.id,
            poi.country.code,
            poi.country.name,
            poi.quantity,
            poi.receivedQuantity,
            poi.status.code
        )
        FROM PurchaseOrderItem poi
        WHERE poi.purchaseOrder.id = :purchaseOrderId
        ORDER BY poi.id
    """)
    List<AdminPurchaseOrderItemDto> getPurchaseOrderItems(
            @Param("purchaseOrderId") Integer purchaseOrderId
    );

    Optional<PurchaseOrderItem> findByIdAndPurchaseOrderId(
            Integer itemId,
            Integer purchaseOrderId
    );

    void deleteByPurchaseOrderId(Integer purchaseOrderId);

    boolean existsByPurchaseOrderIdAndReceivedQuantityGreaterThan(
            Integer purchaseOrderId,
            Integer receivedQuantity
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT poi
        FROM PurchaseOrderItem poi
        WHERE poi.id = :itemId
            AND poi.purchaseOrder.id = :purchaseOrderId
    """)
    Optional<PurchaseOrderItem> findByIdAndPurchaseOrderIdForUpdate(
            @Param("itemId") Integer itemId,
            @Param("purchaseOrderId") Integer purchaseOrderId
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT poi
    FROM PurchaseOrderItem poi
    WHERE poi.purchaseOrder.id = :purchaseOrderId
""")
    List<PurchaseOrderItem> findAllByPurchaseOrderIdForUpdate(
            @Param("purchaseOrderId") Integer purchaseOrderId
    );
}
