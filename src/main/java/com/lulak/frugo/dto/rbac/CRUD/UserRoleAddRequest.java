package com.lulak.frugo.dto.rbac.CRUD;

import javax.validation.constraints.NotNull;

public class UserRoleAddRequest {

    @NotNull
    private Integer roleId;

    public Integer getRoleId(){ return roleId; }
    public void setRoleId(Integer roleId){ this.roleId = roleId; }
}
