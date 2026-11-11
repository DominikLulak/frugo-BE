package com.lulak.frugo.service.order;

import com.lulak.frugo.dto.OrderItemDto;
import com.lulak.frugo.dto.order.*;
import com.lulak.frugo.model.order.Order;
import com.lulak.frugo.repository.order.OrderItemRepository;
import com.lulak.frugo.repository.order.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public AdminOrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository
    ){
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<AdminOrderListDto> getFilteredOrders(
            String orderNumber,
            String customerName,
            String statusCode
    ){
        return orderRepository.getFilteredOrders(
                orderNumber,
                customerName,
                statusCode
        );
    }

    @Transactional
    public OrderDetailDto getOrderDetail(
            Integer orderId
    ){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException(
                        "Order not found " + orderId
                ));
        List<AdminOrderItemDetailDto> items =
                orderItemRepository.getOrderItems(orderId);

        return new OrderDetailDto(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getCreatedAt(),
                order.getStatus().getCode(),
                items
        );
    }
}
