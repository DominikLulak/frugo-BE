package com.lulak.frugo.service.order;

import com.lulak.frugo.dto.order.*;
import com.lulak.frugo.model.order.Order;
import com.lulak.frugo.repository.order.OrderItemRepository;
import com.lulak.frugo.repository.order.OrderRepository;
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
            String statusCode,
            String customerName
    ){
        return orderRepository.getFilteredOrders(
                orderNumber,
                statusCode,
                customerName
        );
    }


    public List<AdminOrderItemDetailDto> getOrderItems(
            Integer orderId
    ){
        return orderItemRepository.getOrderItems(orderId);
    }
}
