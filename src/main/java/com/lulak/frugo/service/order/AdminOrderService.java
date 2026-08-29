package com.lulak.frugo.service.order;

import com.lulak.frugo.dto.order.AdminOrderDetailDto;
import com.lulak.frugo.dto.order.AdminOrderDto;
import com.lulak.frugo.dto.order.AdminOrderItemDto;
import com.lulak.frugo.model.order.Order;
import com.lulak.frugo.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;

    public AdminOrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public List<AdminOrderDto> getFilteredOrders(
            String orderNumber,
            String status,
            String customerName
    ){
        return orderRepository.getFilteredOrders(
                orderNumber,
                status,
                customerName
        ).stream()
                .map(order -> new AdminOrderDto(
                        order.getId(),
                        order.getOrderNumber(),
                        order.getCustomer().getId(),
                        order.getCustomer().getName(),
                        order.getStatus().getCode(),
                        order.getStatus().getName(),
                        order.getCreatedAt()
                ))
                .toList();
    }

    public AdminOrderDetailDto getOrderDetail(Integer id){

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found!"));

        List<AdminOrderItemDto> items = order.getItems()
                .stream()
                .map(item -> new AdminOrderItemDto(
                        item.getId(),
                        item.getWarehouseItem().getProduct().getId(),
                        item.getWarehouseItem().getProduct().getName(),
                        item.getQuantity(),
                        item.getPickedQuantity(),
                        item.getStatus().getCode(),
                        item.getStatus().getName()
                ))
                .toList();

        return new AdminOrderDetailDto(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getStatus().getCode(),
                order.getStatus().getName(),
                order.getCreatedAt(),
                items
        );
    }
}
