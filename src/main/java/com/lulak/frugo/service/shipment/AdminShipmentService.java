package com.lulak.frugo.service.shipment;

import com.lulak.frugo.dto.shipment.AdminShipmentDetailDto;
import com.lulak.frugo.dto.shipment.AdminShipmentItemDto;
import com.lulak.frugo.dto.shipment.AdminShipmentListDto;
import com.lulak.frugo.model.shipment.Shipment;
import com.lulak.frugo.repository.shipment.ShipmentItemRepository;
import com.lulak.frugo.repository.shipment.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentItemRepository shipmentItemRepository;

    public AdminShipmentService(
            ShipmentRepository shipmentRepository,
            ShipmentItemRepository shipmentItemRepository
    ){
        this.shipmentRepository = shipmentRepository;
        this.shipmentItemRepository = shipmentItemRepository;
    }

    public List<AdminShipmentListDto> getFilteredShipments(
            String shipmentNumber,
            String orderNumber,
            String statusCode
    ){
        return shipmentRepository.getFiltered(
                shipmentNumber,
                orderNumber,
                statusCode
        );
    }

    public AdminShipmentDetailDto getShipmentDetail(
            Integer id
    ){
        Shipment shipment = shipmentRepository.findShipmentById(id);

        if(shipment == null){
            throw new RuntimeException("Shipment not found");
        }

        List<AdminShipmentItemDto> pallets =
                shipmentItemRepository.getShipmentPallet(id);

        return new AdminShipmentDetailDto(
                shipment.getShipmentNumber(),
                shipment.getOrder().getOrderNumber(),
                shipment.getStatus().getCode(),
                pallets
        );
    }
}
