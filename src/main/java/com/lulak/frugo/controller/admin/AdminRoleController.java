package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.rbac.CRUD.RoleCreateDto;
import com.lulak.frugo.dto.rbac.role.AdminRoleDetailDto;
import com.lulak.frugo.dto.rbac.role.AdminRoleListDto;
import com.lulak.frugo.model.auth.Role;
import com.lulak.frugo.service.rbac.CRUD.RoleCrudService;
import com.lulak.frugo.service.rbac.role.AdminRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
public class AdminRoleController {

    private final AdminRoleService adminRoleService;
    private final RoleCrudService roleCrudService;

    public AdminRoleController(
            AdminRoleService adminRoleService,
            RoleCrudService roleCrudService
    ){
        this.adminRoleService = adminRoleService;
        this.roleCrudService = roleCrudService;
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

    @PostMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public ResponseEntity<Role> createRole(
            @Valid @RequestBody RoleCreateDto dto
    ){
        Role role = roleCrudService.createRole(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public ResponseEntity<Role> updateRole(
            @PathVariable Integer id,
            @Valid @RequestBody RoleCreateDto dto
    ){
        Role role = roleCrudService.updateRole(id, dto);

        return ResponseEntity.ok(role);
    }
}
