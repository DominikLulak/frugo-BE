package com.lulak.frugo.dto.rbac.role;

public class AdminRoleListDto {

    private Integer id;
    private String code;
    private String name;

    public AdminRoleListDto(
            Integer id,
            String code,
            String name
    ){
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Integer getId(){ return id; }
    public String getCode(){ return code; }
    public String getName(){ return name; }
}
