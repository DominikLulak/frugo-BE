package com.lulak.frugo.repository.auth;

import com.lulak.frugo.model.auth.EmployeeRole;
import com.lulak.frugo.model.auth.EmployeeRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, EmployeeRoleId> {

    List<EmployeeRole> findByEmployee_Id(Integer employeeId);

    @Query("""
        SELECT DISTINCT rp.permission.code
        FROM EmployeeRole er
        JOIN RolePermission rp ON rp.role.id = er.role.id
        WHERE er.employee.id = :employeeId
    """)
    List<String> findPermissionCodeByEmployeeId(@Param("employeeId") Integer employeeId);
}
