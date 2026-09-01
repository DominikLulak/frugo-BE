package com.lulak.frugo.repository.shipment;

import com.lulak.frugo.dto.shipment.AdminShipmentListDto;
import com.lulak.frugo.model.shipment.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.shipment.AdminShipmentListDto(
            s.id,
            s.shipmentNumber,
            s.order.orderNumber,
            s.status.code
        )
        FROM Shipment s
        WHERE
            (
                COALESCE(:shipmentNumber, '') = ''
                OR s.shipmentNumber LIKE CONCAT('%', COALESCE(:shipmentNumber, ''), '%') 
            )
        
        AND (
            COALESCE(:orderNumber, '') = ''
            OR s.order.orderNumber LIKE CONCAT('%', COALESCE(:orderNumber, ''), '%') 
        )
        
        AND (
            COALESCE(:statusCode, '') = ''
            OR s.status.code = COALESCE(:statusCode, '') 
        )
    """)
    List<AdminShipmentListDto> getFiltered(
            @Param("shipmentNumber") String shipmentNumber,
            @Param("orderNumber") String orderNumber,
            @Param("statusCode") String statusCode
    );

    @Query("""
        SELECT s
        FROM Shipment s
        WHERE s.id = :id
    """)
    Shipment findShipmentById(
            @Param("id") Integer id
    );
}
