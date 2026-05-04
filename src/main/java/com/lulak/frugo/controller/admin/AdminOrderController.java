package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.AdminOrderDetailDto;
import com.lulak.frugo.dto.AdminOrderDto;
import com.lulak.frugo.model.OrderItem;
import com.lulak.frugo.model.OrderStatus;
import com.lulak.frugo.repository.OrderItemRepository;
import com.lulak.frugo.repository.OrderRepository;
import com.lulak.frugo.service.AdminOrderService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@CrossOrigin("*")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;
    private final OrderItemRepository orderItemRepository;

    public AdminOrderController(AdminOrderService adminOrderService,
                                OrderItemRepository orderItemRepository){
        this.adminOrderService = adminOrderService;
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping
    public List<AdminOrderDto> getOrders(
            @RequestParam(required = false) String orderNumber,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String customerName
    ){
        return adminOrderService.getFilteredOrders(orderNumber, status, customerName);
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
