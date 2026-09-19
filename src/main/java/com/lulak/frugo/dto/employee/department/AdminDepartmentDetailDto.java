package com.lulak.frugo.dto.employee.department;

import java.util.List;

public class AdminDepartmentDetailDto {

    private Integer id;
    private String code;
    private String name;
    private String description;
    private List<AdminJobPositionListDto> positions;

    public AdminDepartmentDetailDto(
            Integer id,
            String code,
            String name,
            String description,
            List<AdminJobPositionListDto> positions
    ){
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.positions = positions;
    }

    public Integer getId(){ return id; }
    public String getCode(){ return code; }
    public String getName(){ return name; }
    public String getDescription(){ return description; }
    public List<AdminJobPositionListDto> getPositions(){ return positions; }
}
