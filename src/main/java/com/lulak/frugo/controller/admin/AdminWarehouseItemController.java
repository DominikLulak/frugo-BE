package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.product.AdminWarehouseItemDetailDto;
import com.lulak.frugo.dto.product.AdminWarehouseItemListDto;
import com.lulak.frugo.service.product.AdminWarehouseItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products/stock")
@CrossOrigin("*")
public class AdminWarehouseItemController {

    private final AdminWarehouseItemService adminWarehouseItemService;

    public AdminWarehouseItemController(
            AdminWarehouseItemService adminWarehouseItemService
    ){
        this.adminWarehouseItemService = adminWarehouseItemService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
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
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminWarehouseItemDetailDto getWarehouseItemDetail(
            @PathVariable Integer id
    ){
        return adminWarehouseItemService.getWarehouseItemDetail(id);
    }
}
