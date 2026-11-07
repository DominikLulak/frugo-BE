package com.lulak.frugo.dto.warehouse.CRUD;

import javax.validation.constraints.NotNull;

public class LocationCreateDto {

    @NotNull
    private String code;

    private Integer aisle;
    private Integer rack;
    private Integer level;
    private Integer position;

    @NotNull
    private boolean canBeOrdered;

    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code; }

    public Integer getAisle(){ return aisle; }
    public void setAisle(Integer aisle){ this.aisle = aisle; }

    public Integer getRack(){ return rack; }
    public void setRack(Integer rack){ this.rack = rack; }

    public Integer getLevel(){ return level; }
    public void setLevel(Integer level){ this.level = level; }

    public Integer getPosition(){ return position; }
    public void setPosition(Integer position){ this.position = position; }

    public boolean isCanBeOrdered(){ return canBeOrdered; }
    public void setCanBeOrdered(boolean canBeOrdered){ this.canBeOrdered = canBeOrdered; }
}
