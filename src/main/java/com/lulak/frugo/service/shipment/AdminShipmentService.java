package com.lulak.frugo.service.shipment;

import com.lulak.frugo.dto.shipment.AdminShipmentDetailDto;
import com.lulak.frugo.dto.shipment.AdminShipmentDto;
import com.lulak.frugo.dto.shipment.AdminShipmentItemDto;
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

    public List<AdminShipmentDto> getFilteredShipments(
            String shipmentNumber,
            String orderNumber,
            String status,
            String customerName
    ){
        return shipmentRepository.getFilteredShipments(
                shipmentNumber,
                orderNumber,
                status,
                customerName
        ).stream()
                .map(shipment -> new AdminShipmentDto(
                        shipment.getShipmentNumber(),
                        shipment.getOrder().getOrderNumber(),
                        shipment.getStatus().getName(),
                        shipment.getOrder().getCustomer().getName()
                ))
                .toList();
    }

    public AdminShipmentDetailDto getShipmentDetail(Integer id){
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found!"));

        List<AdminShipmentItemDto> items =
                shipmentItemRepository.findByShipment_Id(id)
                        .stream()
                        .map(item -> new AdminShipmentItemDto(
                                item.getPallet().getPalletNumber(),
                                item.getStatus().getName()
                        ))
                        .toList();

        return new AdminShipmentDetailDto(
                shipment.getShipmentNumber(),
                shipment.getOrder().getOrderNumber(),
                shipment.getOrder().getCustomer().getName(),
                shipment.getStatus().getName(),
                items
        );
    }
}
