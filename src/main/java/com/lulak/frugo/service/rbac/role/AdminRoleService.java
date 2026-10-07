package com.lulak.frugo.service.rbac.role;

import com.lulak.frugo.dto.rbac.role.AdminRoleDetailDto;
import com.lulak.frugo.dto.rbac.role.AdminRoleListDto;
import com.lulak.frugo.dto.rbac.role.AdminRolePermissionListDto;
import com.lulak.frugo.dto.rbac.role.AdminRoleUserListDto;
import com.lulak.frugo.model.auth.Role;
import com.lulak.frugo.repository.rbac.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class AdminRoleService {

    private final RoleRepository roleRepository;

    public AdminRoleService(
            RoleRepository roleRepository
    ){
        this.roleRepository = roleRepository;
    }

    public List<AdminRoleListDto> getAllRoles(){ return roleRepository.getAllRoles(); }

    public AdminRoleDetailDto getRoleDetail(Integer id){

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found!"));

        List<AdminRolePermissionListDto> rolePermissions =
                roleRepository.getRolePermissions(id);

        List<AdminRoleUserListDto> roleUsers =
                roleRepository.getRoleUsers(id);

        return new AdminRoleDetailDto(
                role.getId(),
                role.getCode(),
                rolePermissions,
                roleUsers
        );
    }
}
