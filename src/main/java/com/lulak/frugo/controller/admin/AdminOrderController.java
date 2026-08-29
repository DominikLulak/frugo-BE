package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.order.AdminOrderDetailDto;
import com.lulak.frugo.dto.order.AdminOrderDto;
import com.lulak.frugo.service.order.AdminOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@CrossOrigin("*")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService){
        this.adminOrderService = adminOrderService;
    }

    @GetMapping
    public List<AdminOrderDto> getOrders(
            @RequestParam(required = false) String orderNumber,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String customerName
    ){
        return adminOrderService.getFilteredOrders(
                orderNumber,
                status,
                customerName
        );
    }

    @GetMapping("/{id}")
    public AdminOrderDetailDto getOrderDetail(
            @PathVariable Integer id
    ){
        return adminOrderService.getOrderDetail(id);
    }
}
