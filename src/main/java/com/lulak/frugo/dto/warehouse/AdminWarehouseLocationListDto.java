package com.lulak.frugo.dto.warehouse;

public class AdminWarehouseLocationListDto {

    private Integer id;
    private String code;
    private Integer aisle;
    private Integer rack;
    private Integer level;
    private Integer position;
    private boolean canBeOrdered;

    public AdminWarehouseLocationListDto(
            Integer id,
            String code,
            Integer aisle,
            Integer rack,
            Integer level,
            Integer position,
            boolean canBeOrdered
    ){
        this.id = id;
        this.code = code;
        this.aisle = aisle;
        this.rack = rack;
        this.level = level;
        this.position = position;
        this.canBeOrdered = canBeOrdered;
    }

    public Integer getId(){ return id; }
    public String getCode(){ return code; }
    public Integer getAisle(){ return aisle; }
    public Integer getRack(){ return rack; }
    public Integer getLevel(){ return level; }
    public Integer getPosition(){ return position; }
    public boolean isCanBeOrdered(){ return canBeOrdered; }
}
