package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.rbac.CRUD.RoleCreateDto;
import com.lulak.frugo.dto.rbac.role.AdminRoleDetailDto;
import com.lulak.frugo.dto.rbac.role.AdminRoleListDto;
import com.lulak.frugo.model.auth.Role;
import com.lulak.frugo.service.rbac.role.CRUD.RoleCrudService;
import com.lulak.frugo.service.rbac.role.CRUD.RolePermissionService;
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
    private final RolePermissionService rolePermissionService;

    public AdminRoleController(
            AdminRoleService adminRoleService,
            RoleCrudService roleCrudService,
            RolePermissionService rolePermissionService){
        this.adminRoleService = adminRoleService;
        this.roleCrudService = roleCrudService;
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLES_READ')")
    public List<AdminRoleListDto> getAllRoles(){ return adminRoleService.getAllRoles(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLES_READ')")
    public AdminRoleDetailDto getRoleDetail(
            @PathVariable Integer id
    ){
        return adminRoleService.getRoleDetail(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLES_CREATE')")
    public ResponseEntity<Role> createRole(
            @Valid @RequestBody RoleCreateDto dto
    ){
        Role role = roleCrudService.createRole(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLES_EDIT')")
    public ResponseEntity<Role> updateRole(
            @PathVariable Integer id,
            @Valid @RequestBody RoleCreateDto dto
    ){
        Role role = roleCrudService.updateRole(id, dto);

        return ResponseEntity.ok(role);
    }

    @PostMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasAuthority('ROLES_MANAGE')")
    public ResponseEntity<Void> addPermissionToRole(
            @PathVariable Integer roleId,
            @PathVariable Integer permissionId
    ){
        rolePermissionService.addPermissionRoRole(
                roleId,
                permissionId
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasAuthority('ROLES_MANAGE')")
    public ResponseEntity<Void> removePermissionFromRole(
            @PathVariable Integer roleId,
            @PathVariable Integer permissionId
    ){
        rolePermissionService.removePermissionFromRole(
                roleId,
                permissionId
        );

        return ResponseEntity.noContent().build();
    }
}
