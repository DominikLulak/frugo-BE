package com.lulak.frugo.service;

import com.lulak.frugo.dto.AdminOrderDto;
import com.lulak.frugo.model.OrderStatus;
import com.lulak.frugo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;

    public AdminOrderService(OrderRepository orderRepository){this.orderRepository = orderRepository;}

    public List<AdminOrderDto> getFilteredOrders(
            String orderNumber,
            String status,
            String customerName
    ){
        OrderStatus statusEnum = null;

        if(status != null && !status.isBlank()){
            try{
                statusEnum = OrderStatus.valueOf(status.toUpperCase());
            }catch (IllegalArgumentException e){
                statusEnum = null;
            }
        }

        return orderRepository.getFilteredOrders(
                orderNumber,
                statusEnum,
                customerName
        ).stream()
                .map(order -> new AdminOrderDto(
                        order.getOrderNumber(),
                        order.getStatus().name(),
                        order.getCustomerName()
                ))
                .toList();
    }
}
