package com.lulak.frugo.model.warehouse;

import jakarta.persistence.*;

@Entity
@Table(
        name = "sector",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_sector_warehouse_code",
                        columnNames = {"warehouse_id", "code"}
                ),
                @UniqueConstraint(
                        name = "uk_sector_warehouse_name",
                        columnNames = {"warehouse_id", "name"}
                )
        }
)
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private SectorType type;

    private String description;

    public Integer getId(){ return id; }

    public Warehouse getWarehouse(){ return warehouse; }
    public void setWarehouse(Warehouse warehouse){ this.warehouse = warehouse; }

    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public SectorType getType(){ return type; }
    public void setType(SectorType type){ this.type = type; }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }
}
