package com.lulak.frugo.service.rbac.role.CRUD;

import com.lulak.frugo.model.auth.Permission;
import com.lulak.frugo.model.auth.Role;
import com.lulak.frugo.model.auth.RolePermission;
import com.lulak.frugo.model.auth.RolePermissionId;
import com.lulak.frugo.repository.rbac.PermissionRepository;
import com.lulak.frugo.repository.rbac.RolePermissionRepository;
import com.lulak.frugo.repository.rbac.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionService(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            RolePermissionRepository rolePermissionRepository
    ){
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Transactional
    public void addPermissionRoRole(
            Integer roleId,
            Integer permissionId
    ){
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found " + roleId));

        if("GOD".equals(role.getCode())){
            throw new IllegalStateException("GOD role permission cannot be edited!");
        }

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found " + permissionId));

        if(rolePermissionRepository.existsByRole_IdAndPermission_Id(roleId, permissionId)){
            throw new RuntimeException("Permission already assigned to this role");
        }

        RolePermission rolePermission = new RolePermission();

        rolePermission.setId(new RolePermissionId(roleId, permissionId));
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);

        rolePermissionRepository.save(rolePermission);
    }

    @Transactional
    public void removePermissionFromRole(
            Integer roleId,
            Integer permissionId
    ){
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found " + roleId));
        if("GOD".equals(role.getCode())){
            throw new IllegalStateException("GOD role permission cannot be edited!");
        }

        if(!rolePermissionRepository.existsByRole_IdAndPermission_Id(roleId, permissionId)){
            throw new RuntimeException("Permission is not assigned to this role");
        }

        rolePermissionRepository.deleteByRole_IdAndPermission_Id(roleId, permissionId);
    }
}
