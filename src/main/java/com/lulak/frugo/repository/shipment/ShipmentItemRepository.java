package com.lulak.frugo.repository.shipment;

import com.lulak.frugo.dto.shipment.AdminShipmentItemDto;
import com.lulak.frugo.model.shipment.ShipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShipmentItemRepository extends JpaRepository<ShipmentItem, Integer> {

    @Query("""
        SELECT NEW com.lulak.frugo.dto.shipment.AdminShipmentItemDto(
            si.pallet.id,
            si.pallet.palletNumber,
            si.pallet.closed,
            si.status.code
        )
        FROM ShipmentItem si
        WHERE si.shipment.id = :shipmentId
    """)
    List<AdminShipmentItemDto> getShipmentPallet(
            @Param("shipmentId")Integer shipmentId
    );
}
