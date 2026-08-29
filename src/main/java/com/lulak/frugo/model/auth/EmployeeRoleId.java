package com.lulak.frugo.model.auth;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeRoleId implements Serializable {

    private Integer employeeId;
    private Integer roleId;

    public EmployeeRoleId(){ }

    public EmployeeRoleId(Integer employeeId, Integer roleId){
        this.employeeId = employeeId;
        this.roleId = roleId;
    }

    public Integer getEmployeeId(){ return employeeId; }
    public void setEmployeeId(Integer employeeId){ this.employeeId = employeeId; }

    public Integer getRoleId(){ return roleId; }
    public void setRoleId(Integer roleId){ this.roleId = roleId; }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof EmployeeRoleId that)) return false;

        return Objects.equals(employeeId, that.employeeId)
                && Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(employeeId, roleId);
    }
}
