package com.lulak.frugo.service.purchaseOrders;

import com.lulak.frugo.dto.purchaseOrders.AdminPurchaseOrderDetailDto;
import com.lulak.frugo.dto.purchaseOrders.AdminPurchaseOrderItemDto;
import com.lulak.frugo.dto.purchaseOrders.AdminPurchaseOrderListDto;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrder;
import com.lulak.frugo.repository.purchaseOrders.PurchaseOrderItemRepository;
import com.lulak.frugo.repository.purchaseOrders.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;

    public AdminPurchaseOrderService(
            PurchaseOrderRepository purchaseOrderRepository,
            PurchaseOrderItemRepository purchaseOrderItemRepository
    ){
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
    }

    public List<AdminPurchaseOrderListDto> getFilteredPurchaseOrders(
            String orderNumber,
            String supplierName,
            String employeeName,
            String statusCode
    ){
        return purchaseOrderRepository.getFilteredPurchaseOrders(
                orderNumber,
                supplierName,
                employeeName,
                statusCode
        );
    }

    public AdminPurchaseOrderDetailDto getPurchaseOrderDetail(
            Integer id
    ){
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findPurchaseOrderById(id);

        if(purchaseOrder == null){
            throw new RuntimeException("Purchase order not found! " + id);
        }

        List<AdminPurchaseOrderItemDto> items =
                purchaseOrderItemRepository.getPurchaseOrderItems(id);

        return new AdminPurchaseOrderDetailDto(
                purchaseOrder.getPurchaseOrderNumber(),
                purchaseOrder.getSupplier().getName(),
                purchaseOrder.getSupplier().getInternalCode(),
                purchaseOrder.getCreatedAt(),
                purchaseOrder.getEmployee().getFirstName() + " " + purchaseOrder.getEmployee().getLastName(),
                purchaseOrder.getStatus().getCode(),
                items
        );
    }
}
