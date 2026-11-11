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

    @Query("""
        SELECT CASE WHEN COUNT(er) > 0 THEN true ELSE false END 
        FROM EmployeeRole er
        JOIN EmployeeLogin el ON el.employee.id = er.employee.id
        WHERE el.username = :username
        AND er.role.code = :roleCode
    """)
    boolean existsByUsernameAndRoleCode(
            @Param("username") String username,
            @Param("roleCoe") String roleCode
    );

    @Query("""
        SELECT COUNT(DISTINCT er.employee.id)
        FROM EmployeeRole er
        JOIN EmployeeLogin el ON el.employee.id = er.employee.id
        WHERE er.role.code = :roleCode
        AND er.employee.active = true
        AND er.employee.id <> :employeeId
    """)
    long countActiveGodUsersExcludingEmployee(
            @Param("employeeId") Integer employeeId,
            @Param("roleCode") String roleCode
    );
}
