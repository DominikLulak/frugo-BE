package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.event.PurchaseOrderStatusHistoryListDto;
import com.lulak.frugo.service.purchaseOrders.PurchaseOrderStatusHistoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/purchaseOrderStatusHistories")
@CrossOrigin("*")
public class PurchaseOrderStatusHistoryController {

    private final PurchaseOrderStatusHistoryService statusHistoryService;

    public PurchaseOrderStatusHistoryController(
            PurchaseOrderStatusHistoryService statusHistoryService
    ){
        this.statusHistoryService = statusHistoryService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public List<PurchaseOrderStatusHistoryListDto> getStatusHistories(
            @RequestParam(required = false) String purchaseOrderNumber,
            @RequestParam(required = false) String employeeName
    ){
        return statusHistoryService.getFilteredStatusHistories(
                purchaseOrderNumber,
                employeeName
        );
    }
}
