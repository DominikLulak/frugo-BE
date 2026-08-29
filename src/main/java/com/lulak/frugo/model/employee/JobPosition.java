package com.lulak.frugo.model.employee;

import jakarta.persistence.*;

@Entity
@Table(name = "job_position")
public class JobPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    public JobPosition(){ }

    public Integer getId(){ return id; }

    public Department getDepartment(){ return department; }
    public void setDepartment(Department department){ this.department = department; }

    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }
}
