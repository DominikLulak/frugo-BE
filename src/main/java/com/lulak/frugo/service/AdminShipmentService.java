package com.lulak.frugo.service;

import com.lulak.frugo.dto.AdminShipmentDto;
import com.lulak.frugo.model.Status;
import com.lulak.frugo.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminShipmentService {

    private final ShipmentRepository shipmentRepository;

    public AdminShipmentService(ShipmentRepository shipmentRepository){this.shipmentRepository = shipmentRepository;}

    public List<AdminShipmentDto> getFilteredShipments(
            String shipmentNumber,
            String orderNumber,
            String status,
            String customerName
    ){
        Status statusEnum = null;

        if(status != null && !status.isBlank()){
            try{
                statusEnum = Status.valueOf(status.toUpperCase());
            }catch (IllegalArgumentException e){
                statusEnum = null;
            }
        }

        return shipmentRepository.getFilteredShipments(
                shipmentNumber,
                orderNumber,
                statusEnum,
                customerName
        ).stream()
                .map(shipment -> new AdminShipmentDto(
                        shipment.getShipmentNumber(),
                        shipment.getOrderNumber(),
                        shipment.getStatus().name(),
                        shipment.getCustomerName()
                ))
                .toList();
    }
}
