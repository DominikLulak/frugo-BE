package com.lulak.frugo.model.warehouse;

import jakarta.persistence.*;

@Entity
@Table(
        name = "location",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_location_sector_code",
                        columnNames = {"sector_id", "code"}
                )
        }
)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    @Column(nullable = false)
    private String code;

    private Integer aisle;

    private Integer rack;

    private Integer level;

    private Integer position;

    @Column(name = "can_be_ordered", nullable = false)
    private boolean canBeOrdered;

    public Integer getId(){ return id; }

    public Sector getSector(){ return sector; }
    public void setSector(Sector sector){ this.sector = sector; }

    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code; }

    public Integer getAisle(){ return aisle; }
    public void setAisle(Integer aisle){ this.aisle = aisle; }

    public Integer getRack(){ return rack; }
    public void setRack(Integer rack){ this.rack = rack; }

    public Integer getLevel(){ return level; }
    public void setLevel(Integer level){ this.level = level; }

    public Integer getPosition(){return position; }
    public void setPosition(Integer position){ this.position = position; }

    public boolean isCanBeOrdered(){ return canBeOrdered; }
    public void setCanBeOrdered(boolean canBeOrdered){ this.canBeOrdered = canBeOrdered; }
}
