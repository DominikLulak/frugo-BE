package com.lulak.frugo.service.rbac.CRUD;

import com.lulak.frugo.dto.rbac.CRUD.RoleCreateDto;
import com.lulak.frugo.model.auth.Role;
import com.lulak.frugo.repository.rbac.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RoleCrudService {

    private final RoleRepository roleRepository;

    public RoleCrudService(
            RoleRepository roleRepository
    ){
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Role createRole(
            RoleCreateDto dto
    ){
        if(roleRepository.existsByCode(dto.getCode())){
            throw new RuntimeException("Role code already exists.");
        }
        if(roleRepository.existsByName(dto.getName())){
            throw new RuntimeException("Role name already exists.");
        }

        Role role = new Role();

        role.setCode(dto.getCode());
        role.setName(dto.getName());

        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    public Role updateRole(
            Integer roleId,
            RoleCreateDto dto
    ){
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found " + roleId));

        if(!role.getCode().equals(dto.getCode())
            && roleRepository.existsByCodeAndIdNot((dto.getCode()), roleId)){
            throw new RuntimeException("Role code already exists");
        }
        if(!role.getName().equals(dto.getName())
                && roleRepository.existsByNameAndIdNot((dto.getName()), roleId)){
            throw new RuntimeException("Role name already exists");
        }
        if(dto.getCode().equals("GOD")){
            throw new RuntimeException("Cannot edit GOD role!!");
        }

        role.setCode(dto.getCode());
        role.setName(dto.getName());

        return roleRepository.save(role);
    }
}
