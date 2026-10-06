package com.lulak.frugo.dto.rbac.role;

import java.util.List;

public class AdminRoleDetailDto {

    private Integer id;
    private String roleCode;
    private List<AdminRolePermissionListDto> permissions;
    private List<AdminRoleUserListDto> users;

    public AdminRoleDetailDto(
            Integer id,
            String roleCode,
            List<AdminRolePermissionListDto> permissions,
            List<AdminRoleUserListDto> users
    ){
        this.id = id;
        this.roleCode = roleCode;
        this.permissions = permissions;
        this.users = users;
    }

    public Integer getId(){ return id; }
    public String getRoleCode(){ return roleCode; }
    public List<AdminRolePermissionListDto> getPermissions(){ return permissions; }
    public List<AdminRoleUserListDto> getUsers(){ return users; }
}
