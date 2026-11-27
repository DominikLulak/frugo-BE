package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.product.AdminWarehouseItemDetailDto;
import com.lulak.frugo.dto.product.AdminWarehouseItemListDto;
import com.lulak.frugo.dto.product.transactions.MoveStockItemDto;
import com.lulak.frugo.dto.product.transactions.SplitStockItemDto;
import com.lulak.frugo.model.product.WarehouseItem;
import com.lulak.frugo.service.product.AdminWarehouseItemService;
import com.lulak.frugo.service.product.transactions.WarehouseItemMovementService;
import com.lulak.frugo.service.product.transactions.WarehouseItemSplitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products/stock")
@CrossOrigin("*")
public class AdminWarehouseItemController {

    private final AdminWarehouseItemService adminWarehouseItemService;
    private final WarehouseItemMovementService warehouseItemMovementService;
    private final WarehouseItemSplitService warehouseItemSplitService;

    public AdminWarehouseItemController(
            AdminWarehouseItemService adminWarehouseItemService,
            WarehouseItemMovementService warehouseItemMovementService,
            WarehouseItemSplitService warehouseItemSplitService
    ){
        this.adminWarehouseItemService = adminWarehouseItemService;
        this.warehouseItemMovementService = warehouseItemMovementService;
        this.warehouseItemSplitService = warehouseItemSplitService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('STOCKS_READ')")
    public List<AdminWarehouseItemListDto> getWarehouseItems(
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String productType,
            @RequestParam(required = false) String etiNumber,
            @RequestParam(required = false) String warehouseCode
    ){
        return adminWarehouseItemService.getFilteredWarehouseItems(
                productCode,
                name,
                category,
                productType,
                etiNumber,
                warehouseCode
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('STOCKS_READ')")
    public AdminWarehouseItemDetailDto getWarehouseItemDetail(
            @PathVariable Integer id
    ){
        return adminWarehouseItemService.getWarehouseItemDetail(id);
    }

    @PostMapping("/move")
    @PreAuthorize("hasAuthority('STOCKS_MOVE')")
    public void moveWarehouseItem(
            @RequestBody MoveStockItemDto dto
    ){
        warehouseItemMovementService.moveWarehouseItem(dto);
    }

    @PostMapping("/split")
    @PreAuthorize("hasAuthority('STOCKS_SPLIT')")
    public ResponseEntity<WarehouseItem> splitWarehouseItem(
            @Valid @RequestBody SplitStockItemDto dto
    ){
        WarehouseItem newWarehouseItem = warehouseItemSplitService.splitWarehouseItem(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newWarehouseItem);
    }
}
