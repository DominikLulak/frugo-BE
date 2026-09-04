package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.purchaseOrders.AdminPurchaseOrderDetailDto;
import com.lulak.frugo.dto.purchaseOrders.AdminPurchaseOrderListDto;
import com.lulak.frugo.service.purchaseOrders.AdminPurchaseOrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/purchaseOrders")
@CrossOrigin("*")
public class AdminPurchaseOrderController {

    private final AdminPurchaseOrderService purchaseOrderService;

    public AdminPurchaseOrderController(
            AdminPurchaseOrderService purchaseOrderService
    ){
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public List<AdminPurchaseOrderListDto> getPurchaseOrders(
            @RequestParam(required = false) String purchaseOrderNumber,
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) String employeeName,
            @RequestParam(required = false) String statusCode
    ){
        return purchaseOrderService.getFilteredPurchaseOrders(
                purchaseOrderNumber,
                supplierName,
                employeeName,
                statusCode
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public AdminPurchaseOrderDetailDto getPurchaseOrderDetail(
            @PathVariable Integer id
    ){
        return purchaseOrderService.getPurchaseOrderDetail(id);
    }
}
