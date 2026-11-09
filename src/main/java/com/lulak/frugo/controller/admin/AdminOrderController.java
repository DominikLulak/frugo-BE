package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.order.AdminOrderItemDetailDto;
import com.lulak.frugo.dto.order.AdminOrderListDto;
import com.lulak.frugo.dto.order.CRUD.*;
import com.lulak.frugo.dto.order.OrderDetailDto;
import com.lulak.frugo.model.order.Order;
import com.lulak.frugo.model.order.OrderStatusHistory;
import com.lulak.frugo.service.order.AdminOrderService;
import com.lulak.frugo.service.order.OrderCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@CrossOrigin("*")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;
    private final OrderCRUDService orderCRUDService;

    public AdminOrderController(
            AdminOrderService adminOrderService,
            OrderCRUDService orderCRUDService
    ){
        this.adminOrderService = adminOrderService;
        this.orderCRUDService = orderCRUDService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ORDERS_READ')")
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ORDERS_READ')")
    public OrderDetailDto getOrderDetail(
            @PathVariable Integer id
    ){
        return adminOrderService.getOrderDetail(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORDERS_CREATE')")
    public ResponseEntity<Order> createOrder(
            @Valid @RequestBody OrderCreateDto dto
    ){
        Order order = orderCRUDService.createOrder(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAuthority('ORDERS_EDIT')")
    public ResponseEntity<Void> updateOrder(
            @PathVariable Integer orderId,
            @Valid @RequestBody OrderUpdateDto dto
    ){
        orderCRUDService.updateOrder(orderId, dto);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/items")
    @PreAuthorize("hasAuthority('ORDERS_MANAGE')")
    public ResponseEntity<Void> addItem(
            @PathVariable Integer orderId,
            @Valid @RequestBody OrderItemCreateDto dto
    ){
        orderCRUDService.addItem(orderId, dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{orderId}/items/{productId}")
    @PreAuthorize("hasAuthority('ORDERS_MANAGE')")
    public ResponseEntity<Void> updateItem(
            @PathVariable Integer orderId,
            @PathVariable Integer productId,
            @Valid @RequestBody OrderItemUpdateDto dto
    ){
        orderCRUDService.updateItem(orderId, productId, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{orderId}/items/{productId}")
    @PreAuthorize("hasAuthority('ORDERS_MANAGE')")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Integer orderId,
            @PathVariable Integer productId
    ){
        orderCRUDService.deleteItem(orderId, productId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{orderId}/status")
    @PreAuthorize("hasAuthority('ORDERS_MANAGE')")
    public ResponseEntity<Void> changeStatus(
            @PathVariable Integer orderId,
            @Valid @RequestBody OrderStatusChangeDto dto
    ){
        orderCRUDService.changeStatus(orderId, dto);

        return ResponseEntity.noContent().build();
    }
}
