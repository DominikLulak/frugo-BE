package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.purchaseOrders.*;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrder;
import com.lulak.frugo.service.purchaseOrders.AdminPurchaseOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(
            @Valid @RequestBody PurchaseOrderCreateDto dto
    ){
        PurchaseOrder purchaseOrder = purchaseOrderService.cretePurchaseOrder(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(purchaseOrder);
    }

    @PutMapping("/{purchaseOrderId}/items/{itemId}")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<Void> updatePurchaseOrderItem(
            @PathVariable Integer purchaseOrderId,
            @PathVariable Integer itemId,
            @Valid @RequestBody PurchaseOrderItemUpdateDto dto
    ){
        purchaseOrderService.updatePurchaseOrderItem(
                purchaseOrderId,
                itemId,
                dto
        );

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{purchaseOrderId}/items")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<Void> addPurchaseOrderItem(
            @PathVariable Integer purchaseOrderId,
            @Valid @RequestBody PurchaseOrderItemCreateDto dto
    ){
        purchaseOrderService.addPurchaseOrderItem(
                purchaseOrderId,
                dto
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{purchaseOrderId}/items/{itemId}")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<Void> deletePurchaseOrderItem(
            @PathVariable Integer purchaseOrderId,
            @PathVariable Integer itemId
    ){
        purchaseOrderService.deletePurchaseOrderItem(
                purchaseOrderId,
                itemId
        );

        return ResponseEntity.noContent().build();
    }
}
