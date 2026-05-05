package com.lulak.frugo.repository;

import com.lulak.frugo.model.Shipment;
import com.lulak.frugo.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Query("""
        SELECT s FROM Shipment s
        WHERE (:shipmentNumber IS NULL OR s.shipmentNumber LIKE %:shipmentNumber%)
        AND (:orderNumber IS NULL OR s.orderNumber LIKE %:orderNumber)
        AND (:status IS NULL OR s.status = :status)
        AND (:customerName IS NULL OR LOWER(s.customerName) LIKE LOWER(CONCAT('%', :customerName, '%')))
    """)
    List<Shipment> getFilteredShipments(
            String shipmentNumber,
            String orderNumber,
            Status status,
            String customerName
    );
}
