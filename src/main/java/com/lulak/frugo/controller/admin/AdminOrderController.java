package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.order.AdminOrderItemDetailDto;
import com.lulak.frugo.dto.order.AdminOrderListDto;
import com.lulak.frugo.service.order.AdminOrderService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ORDER_READ')")
    public List<AdminOrderListDto> getOrders(
            @RequestParam(required = false) String orderNumber,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String statusCode

    ){
        return adminOrderService.getFilteredOrders(
                orderNumber,
                customerName,
                statusCode
        );
    }

    @GetMapping("/{id}/items")
    @PreAuthorize("hasAuthority('ORDER_READ')")
    public List<AdminOrderItemDetailDto> getOrderItems(
            @PathVariable Integer id
    ){
        return adminOrderService.getOrderItems(id);
    }
}
