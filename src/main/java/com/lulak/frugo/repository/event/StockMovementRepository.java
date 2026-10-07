package com.lulak.frugo.repository.event;

import com.lulak.frugo.dto.event.AdminStockMovementDetailDto;
import com.lulak.frugo.dto.event.AdminStockMovementListDto;
import com.lulak.frugo.model.event.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.event.AdminStockMovementListDto(
            sm.id,
            ed.code,
            wi.etiNumber,
            sm.createdAt,
            sm.quantity,
            fl.code,
            tl.code,
            e.employeeNumber
        )
        FROM StockMovement sm
        JOIN sm.eventDefinition ed
        JOIN sm.warehouseItem wi
        LEFT JOIN sm.fromLocation fl
        LEFT JOIN sm.toLocation tl
        JOIN sm.employee e
        WHERE(
            COALESCE(:eventCode, '') = ''
            OR LOWER(ed.code)
                LIKE LOWER(CONCAT('%', :eventCode, '%')) 
        )
        AND(
            COALESCE(:etiNumber, '') = ''
            OR LOWER(wi.etiNumber)
                LIKE LOWER(CONCAT('%', :etiNumber, '%')) 
        )
        AND(
            COALESCE(:fromLocation, '') = ''
            OR LOWER(fl.code)
                LIKE LOWER(CONCAT('%', :fromLocation, '%')) 
        )
        AND(
            COALESCE(:toLocation, '') = ''
            OR LOWER(tl.code)
                LIKE LOWER(CONCAT('%', :toLocation, '%')) 
        )
        AND(
            COALESCE(:employeeNumber, '') = ''
            OR LOWER(e.employeeNumber)
                LIKE LOWER(CONCAT('%', :employeeNumber, '%')) 
        )
    """)
    List<AdminStockMovementListDto> getFilteredStockMovements(
            @Param("eventCode") String eventCode,
            @Param("etiNumber") String etiNumber,
            @Param("fromLocation") String fromLocation,
            @Param("toLocation") String toLocation,
            @Param("employeeNumber") String employeeNumber
    );

    @Query("""
        SELECT new com.lulak.frugo.dto.event.AdminStockMovementDetailDto(
            sm.id,
            sm.createdAt,
            ed.code,
            ed.name,
            ed.description,
            wi.etiNumber,
            nwi.etiNumber,
            sm.quantity,
            fl.code,
            tl.code,
            e.employeeNumber,
            CONCAT(e.firstName, ' ', e.lastName),
            p.palletNumber,
            o.orderNumber,
            s.shipmentNumber,
            po.purchaseOrderNumber 
        )
        FROM StockMovement sm
        JOIN sm.eventDefinition ed
        JOIN sm.warehouseItem wi
        LEFT JOIN sm.newWarehouseItem nwi
        LEFT JOIN sm.fromLocation fl
        LEFT JOIN sm.toLocation tl
        JOIN sm.employee e
        LEFT JOIN sm.pallet p
        LEFT JOIN sm.order o
        LEFT JOIN sm.shipment s
        LEFT JOIN sm.purchaseOrder po
        WHERE sm.id = :stockMovementId
    """)
    AdminStockMovementDetailDto getStockMovementDetail(
            @Param("stockMovementId") Integer stockMovementId
    );
}
