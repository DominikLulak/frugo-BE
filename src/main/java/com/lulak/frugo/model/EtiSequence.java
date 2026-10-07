package com.lulak.frugo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "eti_sequence")
public class EtiSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "last_number", nullable = false)
    private Integer lastNumber;

    public Integer getId(){ return id; }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }

    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code; }

    public Integer getLastNumber(){ return lastNumber; }
    public void setLastNumber(Integer lastNumber){ this.lastNumber = lastNumber; }
}
