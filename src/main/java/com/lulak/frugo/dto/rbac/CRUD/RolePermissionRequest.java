package com.lulak.frugo.dto.rbac.CRUD;

import javax.validation.constraints.NotNull;

public class RolePermissionRequest {

    @NotNull
    private Integer permissionId;

    public Integer getPermissionId(){ return permissionId; }
    public void setPermissionId(Integer permissionId){ this.permissionId = permissionId; }
}
