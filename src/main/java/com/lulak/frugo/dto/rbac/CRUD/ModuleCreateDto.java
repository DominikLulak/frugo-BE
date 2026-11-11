package com.lulak.frugo.dto.rbac.CRUD;

import javax.validation.constraints.NotNull;

public class ModuleCreateDto {

    @NotNull
    private String moduleCode;

    @NotNull
    private String moduleName;

    public String getModuleCode(){ return moduleCode; }
    public void setModuleCode(String moduleCode){ this.moduleCode = moduleCode; }

    public String getModuleName(){ return moduleName; }
    public void setModuleName(String moduleName){ this.moduleName = moduleName; }
}
