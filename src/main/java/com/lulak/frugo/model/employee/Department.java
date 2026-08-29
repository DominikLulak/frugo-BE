package com.lulak.frugo.model.employee;

import jakarta.persistence.*;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    private String description;

    public Department(){ }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code; }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }
}
