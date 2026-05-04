package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.AdminOrderDetailDto;
import com.lulak.frugo.dto.AdminOrderDto;
import com.lulak.frugo.model.OrderItem;
import com.lulak.frugo.repository.OrderItemRepository;
import com.lulak.frugo.repository.OrderRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@CrossOrigin("*")
public class AdminOrderController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public AdminOrderController(OrderRepository orderRepository,
                                OrderItemRepository orderItemRepository){
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping
    public List<AdminOrderDto> getOrders(){
        return orderRepository.findAll().stream()
                .map(o -> new AdminOrderDto(
                        o.getOrderNumber(),
                        o.getStatus(),
                        o.getCustomerName()
                ))
                .toList();
    }

    @GetMapping("/{orderNumber}")
    public List<AdminOrderDetailDto> getOrderDetail(@PathVariable String orderNumber){
        List<OrderItem> items = orderItemRepository.findByOrderOrderNumber(orderNumber);

        return items.stream()
                .map(i -> new AdminOrderDetailDto(
                        i.getCategory(),
                        i.getName(),
                        i.getVariant(),
                        i.getQuantity()
                ))
                .toList();
    }
}
