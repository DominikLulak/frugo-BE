package com.lulak.frugo.dto.rbac.module;

public class AdminModuleListDto {

    private final Integer id;
    private final String moduleCode;
    private final String moduleName;

    public AdminModuleListDto(
            Integer id,
            String moduleCode,
            String moduleName
    ){
        this.id = id;
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    public Integer getId(){ return id; }
    public String getModuleCode(){ return moduleCode; }
    public String getModuleName(){ return moduleName; }
}
