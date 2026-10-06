package com.lulak.frugo.repository.rbac;

import com.lulak.frugo.dto.rbac.role.AdminRoleListDto;
import com.lulak.frugo.dto.rbac.user.AdminUserDetailDto;
import com.lulak.frugo.dto.rbac.user.AdminUserListDto;
import com.lulak.frugo.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Employee, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.rbac.user.AdminUserListDto(
            e.id,
            e.employeeNumber,
            CONCAT(e.firstName, ' ', e.lastName),
            d.name,
            jp.name
        )
        FROM Employee e
        JOIN e.jobPosition jp
        JOIN jp.department d
        ORDER BY e.id
    """)
    List<AdminUserListDto> getAllUsers();

    @Query("""
        SELECT new com.lulak.frugo.dto.rbac.user.AdminUserDetailDto(
            e.id,
            e.employeeNumber,
            CONCAT(e.firstName, ' ', e.lastName),
            el.username,
            e.systemUsername,
            d.name,
            jp.name,
            null
        )
        FROM Employee e
        LEFT JOIN EmployeeLogin el ON el.employee.id = e.id
        JOIN e.jobPosition jp
        JOIN jp.department d
        WHERE e.id = :userId
    """)
    AdminUserDetailDto getUserDetail(
            @Param("userId") Integer userId
    );

    @Query("""
        SELECT new com.lulak.frugo.dto.rbac.role.AdminRoleListDto(
            r.id,
            r.code,
            r.name
        )
        FROM EmployeeRole er
        JOIN er.role r
        WHERE er.employee.id = :userId
        ORDER BY r.id
    """)
    List<AdminRoleListDto> getUserRoles(
            @Param("userId") Integer userId
    );
}
