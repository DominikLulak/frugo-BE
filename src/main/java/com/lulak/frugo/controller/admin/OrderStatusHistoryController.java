package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.event.OrderStatusHistoryListDto;
import com.lulak.frugo.service.order.OrderStatusHistoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orderStatusHistories")
@CrossOrigin("*")
public class OrderStatusHistoryController {

    private final OrderStatusHistoryService statusHistoryService;

    public OrderStatusHistoryController(
            OrderStatusHistoryService statusHistoryService
    ){
        this.statusHistoryService = statusHistoryService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EVENTS_READ')")
    public List<OrderStatusHistoryListDto> getStatusHistories(
            @RequestParam(required = false) String orderNumber,
            @RequestParam(required = false) String employeeName
    ){
        return statusHistoryService.getFilteredStatusHistories(
                orderNumber,
                employeeName
        );
    }
}
