package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.product.AdminPackagingDto;
import com.lulak.frugo.service.product.AdminPackagingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/warehouses/packaging")
public class AdminPackagingController {

    private final AdminPackagingService adminPackagingService;

    public AdminPackagingController(
            AdminPackagingService adminPackagingService
    ){
        this.adminPackagingService = adminPackagingService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminPackagingDto> getPackaging(){ return adminPackagingService.getAllPackaging(); }
}
