package com.lulak.frugo.service.rbac.module.CRUD;

import com.lulak.frugo.dto.rbac.CRUD.ModuleCreateDto;
import com.lulak.frugo.dto.rbac.CRUD.PermissionCreateDto;
import com.lulak.frugo.model.auth.Permission;
import com.lulak.frugo.repository.rbac.ModuleRepository;
import com.lulak.frugo.model.auth.Module;
import com.lulak.frugo.repository.rbac.PermissionRepository;
import jakarta.transaction.Transactional;

public class ModuleCrudService {

    private final ModuleRepository moduleRepository;
    private final PermissionRepository permissionRepository;

    public ModuleCrudService(
            ModuleRepository moduleRepository,
            PermissionRepository permissionRepository
    ){
        this.moduleRepository = moduleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public Module createModule(
            ModuleCreateDto dto
    ){
        if(moduleRepository.existsByCode(dto.getModuleCode())){
            throw new RuntimeException("Module code already exists");
        }
        if(moduleRepository.existsByName(dto.getModuleName())){
            throw new RuntimeException("Module name already exists");
        }

        Module module = new Module();

        module.setCode(dto.getModuleCode());
        module.setName(dto.getModuleName());

        module = moduleRepository.save(module);
        return module;
    }

    @Transactional
    public Module updateModule(
            Integer moduleId,
            ModuleCreateDto dto
    ){
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found " + moduleId));
        if(!module.getCode().equals(dto.getModuleCode())
            && moduleRepository.existsByCodeAndIdNot((dto.getModuleCode()), moduleId)){
            throw new RuntimeException("Module code already exists");
        }
        if(!module.getName().equals(dto.getModuleName())
                && moduleRepository.existsByNameAndIdNot((dto.getModuleName()), moduleId)){
            throw new RuntimeException("Module name already exists");
        }

        module.setCode(dto.getModuleCode());
        module.setName(dto.getModuleName());

        return moduleRepository.save(module);
    }

    @Transactional
    public Permission createPermission(
            Integer moduleId,
            PermissionCreateDto dto
    ){
        if(permissionRepository.existsByCode(dto.getPermissionCode())){
            throw new RuntimeException("Permission code already exists");
        }
        if(permissionRepository.existsByName(dto.getPermissionName())){
            throw new RuntimeException("Permission name already exists");
        }

        Permission permission = new Permission();
        Module module = moduleRepository.findById(moduleId)
                        .orElseThrow(() -> new RuntimeException("Module not found"));

        permission.setModule(module);
        permission.setCode(dto.getPermissionCode());
        permission.setName(dto.getPermissionName());
        permission.setDescription(dto.getPermissionDescription());

        permission = permissionRepository.save(permission);
        return permission;
    }

    @Transactional
    public Permission updatePermission(
            Integer permissionId,
            PermissionCreateDto dto
    ){
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found " + permissionId));

        if(!permission.getCode().equals(dto.getPermissionCode())
            && permissionRepository.existsByCodeAndIdNot((dto.getPermissionCode()), permissionId)){
            throw new RuntimeException("Permission code already exists");
        }
        if(!permission.getName().equals(dto.getPermissionName())
                && permissionRepository.existsByNameAndIdNot((dto.getPermissionName()), permissionId)){
            throw new RuntimeException("Permission name already exists");
        }

        permission.setCode(dto.getPermissionCode());
        permission.setName(dto.getPermissionName());
        permission.setDescription(dto.getPermissionDescription());
        return permissionRepository.save(permission);
    }
}
