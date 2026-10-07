package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.rbac.module.AdminModuleDetailDto;
import com.lulak.frugo.dto.rbac.module.AdminModuleListDto;
import com.lulak.frugo.service.rbac.module.AdminModuleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/modules")
public class AdminModuleController {

    private final AdminModuleService adminModuleService;

    public AdminModuleController(
            AdminModuleService adminModuleService
    ){
        this.adminModuleService = adminModuleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminModuleListDto> getModules(){ return adminModuleService.getAllModules(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminModuleDetailDto getModuleDetail(
            @PathVariable Integer id
    ){
        return adminModuleService.getModuleDetail(id);
    }
}
