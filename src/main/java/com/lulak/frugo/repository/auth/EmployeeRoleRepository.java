package com.lulak.frugo.repository.auth;

import com.lulak.frugo.model.auth.EmployeeRole;
import com.lulak.frugo.model.auth.EmployeeRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, EmployeeRoleId> {

    List<EmployeeRole> findByEmployee_Id(Integer employeeId);
}
