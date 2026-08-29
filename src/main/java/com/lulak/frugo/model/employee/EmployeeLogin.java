package com.lulak.frugo.model.employee;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_login")
public class EmployeeLogin {

    @Id
    @Column(name = "employee_id")
    private Integer employeeId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    public Integer getEmployeeId(){ return employeeId; }

    public Employee getEmployee(){ return employee; }
    public void setEmployee(Employee employee){ this.employee = employee; }

    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }

    public String getPasswordHash(){ return passwordHash; }
    public void setPasswordHash(String passwordHash){ this.passwordHash = passwordHash; }
}
