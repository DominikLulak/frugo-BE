package com.lulak.frugo.repository.rbac;

import com.lulak.frugo.dto.rbac.module.AdminModuleDetailDto;
import com.lulak.frugo.dto.rbac.module.AdminModuleListDto;
import com.lulak.frugo.dto.rbac.role.AdminRolePermissionListDto;
import com.lulak.frugo.model.auth.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.rbac.module.AdminModuleListDto(
            m.id,
            m.code,
            m.name
        )
        FROM Module m
        ORDER BY m.id
    """)
    List<AdminModuleListDto> getAllModules();

    @Query("""
        SELECT new com.lulak.frugo.dto.rbac.role.AdminRolePermissionListDto(
            p.id,
            m.name,
            p.code,
            p.name,
            p.description
        )
        FROM Permission p
        JOIN p.module m
        WHERE p.module.id = :moduleId
        ORDER BY m.id
    """)
    List<AdminRolePermissionListDto> getModulePermissions(
            @Param("moduleId") Integer moduleId
    );
}
