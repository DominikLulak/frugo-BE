package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.rbac.CRUD.PermissionCreateDto;
import com.lulak.frugo.model.auth.Module;
import com.lulak.frugo.dto.rbac.CRUD.ModuleCreateDto;
import com.lulak.frugo.dto.rbac.module.AdminModuleDetailDto;
import com.lulak.frugo.dto.rbac.module.AdminModuleListDto;
import com.lulak.frugo.model.auth.Permission;
import com.lulak.frugo.service.rbac.module.AdminModuleService;
import com.lulak.frugo.service.rbac.module.CRUD.ModuleCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/modules")
public class AdminModuleController {

    private final AdminModuleService adminModuleService;
    private final ModuleCrudService moduleCrudService;

    public AdminModuleController(
            AdminModuleService adminModuleService,
            ModuleCrudService moduleCrudService
    ){
        this.adminModuleService = adminModuleService;
        this.moduleCrudService = moduleCrudService;
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

    @PostMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public ResponseEntity<Module> createModule(
            @Valid @RequestBody ModuleCreateDto dto
    ){
        Module module = moduleCrudService.createModule(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(module);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public ResponseEntity<Module> updateModule(
            @PathVariable Integer id,
            @Valid @RequestBody ModuleCreateDto dto
    ){
        Module module = moduleCrudService.updateModule(id, dto);

        return ResponseEntity.ok(module);
    }

    @PostMapping("/{moduleId}/permissions")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public ResponseEntity<Permission> createPermission(
            @PathVariable Integer moduleId,
            @Valid @RequestBody PermissionCreateDto dto
    ){
        Permission permission = moduleCrudService.createPermission(moduleId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(permission);
    }

    @PutMapping("/permissions/{permissionId}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public ResponseEntity<Permission> updatePermission(
            @PathVariable Integer permissionId,
            @Valid @RequestBody PermissionCreateDto dto
    ){
        Permission permission = moduleCrudService.updatePermission(permissionId, dto);

        return ResponseEntity.ok(permission);
    }
}
