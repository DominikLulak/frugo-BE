package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.warehouse.AdminWarehouseDetailDto;
import com.lulak.frugo.dto.warehouse.AdminWarehouseListDto;
import com.lulak.frugo.dto.warehouse.AdminWarehouseSectorDetailDto;
import com.lulak.frugo.service.warehouse.AdminWarehouseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/warehouses")
public class AdminWarehouseController {

    private final AdminWarehouseService adminWarehouseService;

    public AdminWarehouseController(
            AdminWarehouseService adminWarehouseService
    ){
        this.adminWarehouseService = adminWarehouseService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminWarehouseListDto> getWarehouses(){
        return adminWarehouseService.getAllWarehouses();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminWarehouseDetailDto getWarehouseDetail(
            @PathVariable Integer id
    ){
        return adminWarehouseService.getWarehouseDetail(id);
    }

    @GetMapping("/sectors/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminWarehouseSectorDetailDto getSector(
            @PathVariable Integer id
    ){
        return adminWarehouseService.getSectorDetail(id);
    }
}
