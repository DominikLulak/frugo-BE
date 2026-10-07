package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.rbac.role.AdminRoleDetailDto;
import com.lulak.frugo.dto.rbac.role.AdminRoleListDto;
import com.lulak.frugo.service.rbac.role.AdminRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
public class AdminRoleController {

    private final AdminRoleService adminRoleService;

    public AdminRoleController(
            AdminRoleService adminRoleService
    ){
        this.adminRoleService = adminRoleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminRoleListDto> getAllRoles(){ return adminRoleService.getAllRoles(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminRoleDetailDto getRoleDetail(
            @PathVariable Integer id
    ){
        return adminRoleService.getRoleDetail(id);
    }
}
