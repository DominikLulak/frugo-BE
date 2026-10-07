package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.warehouse.AdminWarehouseSectorTypeDto;
import com.lulak.frugo.service.warehouse.AdminSectorTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/warehouses/sectorTypes")
public class AdminSectorTypeController {

    private final AdminSectorTypeService adminSectorTypeService;

    public AdminSectorTypeController(
            AdminSectorTypeService adminSectorTypeService
    ){
        this.adminSectorTypeService = adminSectorTypeService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminWarehouseSectorTypeDto> getSectorTypes(){ return adminSectorTypeService.getAllSectorTypes(); }
}
