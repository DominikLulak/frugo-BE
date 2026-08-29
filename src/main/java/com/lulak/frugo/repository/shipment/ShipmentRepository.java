package com.lulak.frugo.repository.shipment;

import com.lulak.frugo.model.order.Order;
import com.lulak.frugo.model.shipment.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

    @Query("""
        SELECT s FROM Shipment s
        WHERE (:shipmentNumber IS NULL 
            OR s.shipmentNumber LIKE CONCAT('%', :shipmentNumber, '%'))
        
        AND (:orderNumber IS NULL
            OR s.order.orderNumber LIKE CONCAT('%', :orderNumber, '%'))
        
        AND (:status IS NULL 
            OR s.status.code = :status)
        
        AND (:customerName IS NULL 
            OR LOWER(s.order.customer.name)
                LIKE LOWER(CONCAT('%', :customerName, '%')))
    """)

    List<Shipment>getFilteredShipments(
            String shipmentNumber,
            String orderNumber,
            String status,
            String customerName
    );

    String order(Order order);
}
