package com.lulak.frugo.repository.shipment;

import com.lulak.frugo.model.shipment.ShipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentItemRepository extends JpaRepository<ShipmentItem, Integer> {

    List<ShipmentItem> findByShipment_Id(Integer shipmentId);
}
