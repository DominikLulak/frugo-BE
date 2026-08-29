package com.lulak.frugo.model.auth;

import com.lulak.frugo.model.employee.Employee;
import jakarta.persistence.*;

@Entity
@Table(name = "employee_role")
public class EmployeeRole {

    @EmbeddedId
    private EmployeeRoleId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public EmployeeRoleId getId(){ return id; }
    public void setId(EmployeeRoleId id){ this.id = id; }

    public Employee getEmployee(){ return employee; }
    public void setEmployee(Employee employee){ this.employee = employee; }

    public Role getRole(){ return role; }
    public void setRole(Role role){ this.role = role; }
}
