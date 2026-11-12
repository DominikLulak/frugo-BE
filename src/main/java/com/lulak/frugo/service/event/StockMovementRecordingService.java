package com.lulak.frugo.service.event;

import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.event.StockMovement;
import com.lulak.frugo.model.event.StockMovementDefinition;
import com.lulak.frugo.model.order.Order;
import com.lulak.frugo.model.pallet.Pallet;
import com.lulak.frugo.model.product.WarehouseItem;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrder;
import com.lulak.frugo.model.shipment.Shipment;
import com.lulak.frugo.model.warehouse.Location;
import com.lulak.frugo.repository.event.StockMovementDefinitionRepository;
import com.lulak.frugo.repository.event.StockMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class StockMovementRecordingService {

    private final StockMovementRepository stockMovementRepository;
    private final StockMovementDefinitionRepository stockMovementDefinitionRepository;

    public StockMovementRecordingService(
            StockMovementRepository stockMovementRepository,
            StockMovementDefinitionRepository stockMovementDefinitionRepository
    ){
        this.stockMovementRepository = stockMovementRepository;
        this.stockMovementDefinitionRepository = stockMovementDefinitionRepository;
    }

    @Transactional
    public Long recordMovement(
            String movementCode,
            WarehouseItem warehouseItem,
            WarehouseItem newWarehouseItem,
            Integer quantity,
            Location fromLocation,
            Location toLocation,
            Employee employee,
            Pallet pallet,
            Order order,
            Shipment shipment,
            PurchaseOrder purchaseOrder
    ){
        StockMovementDefinition definition =
                stockMovementDefinitionRepository.findByCode(movementCode)
                        .orElseThrow(() -> new RuntimeException(
                                "Stock movement definition not found " + movementCode
                        ));

        if(warehouseItem == null){
            throw new IllegalArgumentException(
                    "Warehouse item must not be null"
            );
        }
        if(employee == null){
            throw new IllegalArgumentException(
                    "Employee must not be null"
            );
        }
        if(quantity == null || quantity <= 0){
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        StockMovement movement = new StockMovement();

        movement.setStockMovementDefinition(definition);
        movement.setCreatedAt(LocalDateTime.now());

        movement.setWarehouseItem(warehouseItem);
        movement.setNewWarehouseItem(newWarehouseItem);
        movement.setQuantity(quantity);

        movement.setFromLocation(fromLocation);
        movement.setToLocation(toLocation);

        movement.setEmployee(employee);

        movement.setPallet(pallet);
        movement.setOrder(order);
        movement.setShipment(shipment);
        movement.setPurchaseOrder(purchaseOrder);

        return stockMovementRepository.save(movement).getId();
    }
}
