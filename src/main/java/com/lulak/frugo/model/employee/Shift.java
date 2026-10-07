package com.lulak.frugo.model.employee;

import jakarta.persistence.*;

@Entity
@Table(name = "shift")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String code;

    private String description;

    public Shift(){ }

    public Integer getId(){ return id; }

    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code; }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }
}
