package com.lulak.frugo.model.purchaseOrders;

import jakarta.persistence.*;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "internal_code", nullable = false, unique = true)
    private String internalCode;

    public Integer getId(){ return id; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getInternalCode(){ return internalCode; }
    public void setInternalCode(){ this.internalCode = internalCode; }
}
