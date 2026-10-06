package com.lulak.frugo.dto.rbac.module;

import com.lulak.frugo.dto.rbac.role.AdminRolePermissionListDto;

import java.util.List;

public class AdminModuleDetailDto {

    private Integer id;
    private String moduleCode;
    private String moduleName;
    private List<AdminRolePermissionListDto> permissions;

    public AdminModuleDetailDto(
            Integer id,
            String moduleCode,
            String moduleName,
            List<AdminRolePermissionListDto> permissions
    ){
        this.id = id;
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.permissions = permissions;
    }

    public Integer getId(){ return id; }
    public String getModuleCode(){ return moduleCode; }
    public String getModuleName(){ return moduleName; }
    public List<AdminRolePermissionListDto> getPermissions(){ return permissions; }
}
