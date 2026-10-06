package com.lulak.frugo.service.rbac.module;

import com.lulak.frugo.dto.rbac.module.AdminModuleDetailDto;
import com.lulak.frugo.dto.rbac.module.AdminModuleListDto;
import com.lulak.frugo.dto.rbac.role.AdminRolePermissionListDto;
import com.lulak.frugo.model.auth.Module;
import com.lulak.frugo.repository.rbac.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminModuleService {

    ModuleRepository moduleRepository;

    public AdminModuleService(
            ModuleRepository moduleRepository
    ){
        this.moduleRepository = moduleRepository;
    }

    public List<AdminModuleListDto> getAllModules(){ return moduleRepository.getAllModules(); }

    public AdminModuleDetailDto getModuleDetail(Integer id){
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        List<AdminRolePermissionListDto> permissions =
                moduleRepository.getModulePermissions(id);

        return new AdminModuleDetailDto(
                module.getId(),
                module.getCode(),
                module.getName(),
                permissions
        );
    }
}
