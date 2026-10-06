package com.lulak.frugo.repository.rbac;

import com.lulak.frugo.dto.rbac.role.AdminRoleListDto;
import com.lulak.frugo.dto.rbac.role.AdminRolePermissionListDto;
import com.lulak.frugo.dto.rbac.role.AdminRoleUserListDto;
import com.lulak.frugo.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.rbac.role.AdminRoleListDto(
            r.id,
            r.code,
            r.name
        )
        FROM Role r
        ORDER BY r.id
    """)
    List<AdminRoleListDto> getAllRoles();

    @Query("""
        SELECT new com.lulak.frugo.dto.rbac.role.AdminRolePermissionListDto(
            p.id,
            m.name,
            p.code,
            p.name,
            p.description
        )
        FROM RolePermission rp
        JOIN rp.permission p
        JOIN p.module m
        WHERE rp.role.id = :roleId
        ORDER BY m.id
    """)
    List<AdminRolePermissionListDto> getRolePermissions(
            @Param("roleId") Integer roleId
    );

    @Query("""
        SELECT new com.lulak.frugo.dto.rbac.role.AdminRoleUserListDto(
            e.id,
            e.employeeNumber,
            CONCAT(e.firstName, ' ', e.lastName),
            d.name,
            jp.name
        )
        FROM EmployeeRole er
        JOIN er.employee e
        JOIN e.jobPosition jp
        JOIN jp.department d
        WHERE er.role.id = :roleId
        ORDER BY e.id
    """)
    List<AdminRoleUserListDto> getRoleUsers(
            @Param("roleId") Integer roleId
    );
}
