package com.lulak.frugo.dto.rbac.role;

public class AdminRolePermissionListDto {

    private Integer id;
    private String moduleName;
    private String permissionCode;
    private String permissionName;
    private String permissionDescription;

    public AdminRolePermissionListDto(
            Integer id,
            String moduleName,
            String permissionCode,
            String permissionName,
            String permissionDescription
    ){
        this.id = id;
        this.moduleName = moduleName;
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
        this.permissionDescription = permissionDescription;
    }

    public Integer getId(){ return id; }
    public String getModuleName(){ return moduleName; }
    public String getPermissionCode(){ return permissionCode; }
    public String getPermissionName(){ return permissionName; }
    public String getPermissionDescription(){ return permissionDescription; }
}
