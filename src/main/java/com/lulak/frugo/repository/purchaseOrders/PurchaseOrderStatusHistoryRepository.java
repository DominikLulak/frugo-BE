package com.lulak.frugo.repository.purchaseOrders;

import com.lulak.frugo.model.purchaseOrders.PurchaseOrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderStatusHistoryRepository extends JpaRepository<PurchaseOrderStatusHistory, Integer> {

    List<PurchaseOrderStatusHistory> findByPurchaseOrderIdOrderByChangedAtAsc(
            Integer purchaseOrderId
    );
}
