package com.lulak.frugo.dto.rbac.CRUD;

import javax.validation.constraints.NotNull;

public class PermissionCreateDto {

    @NotNull
    private String permissionCode;

    @NotNull
    private String permissionName;

    @NotNull
    private String permissionDescription;

    public String getPermissionCode(){ return permissionCode; }
    public void setPermissionCode(String permissionCode){ this.permissionCode = permissionCode; }

    public String getPermissionName(){ return permissionName; }
    public void setPermissionName(String permissionName){ this.permissionName = permissionName; }

    public String getPermissionDescription(){ return permissionDescription; }
    public void setPermissionDescription(String permissionDescription){ this.permissionDescription = permissionDescription; }
}
