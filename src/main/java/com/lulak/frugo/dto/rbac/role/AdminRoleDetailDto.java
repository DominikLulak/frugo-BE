package com.lulak.frugo.dto.rbac.role;

import java.util.List;

public class AdminRoleDetailDto {

    private Integer id;
    private String code;
    private String name;
    private List<AdminRolePermissionListDto> permissions;
    private List<AdminRoleUserListDto> users;

    public AdminRoleDetailDto(
            Integer id,
            String code,
            String name,
            List<AdminRolePermissionListDto> permissions,
            List<AdminRoleUserListDto> users
    ){
        this.id = id;
        this.code = code;
        this.name = name;
        this.permissions = permissions;
        this.users = users;
    }

    public Integer getId(){ return id; }
    public String getCode(){ return code; }
    public String getName(){ return name; }
    public List<AdminRolePermissionListDto> getPermissions(){ return permissions; }
    public List<AdminRoleUserListDto> getUsers(){ return users; }
}
