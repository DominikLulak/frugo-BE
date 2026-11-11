package com.lulak.frugo.repository.rbac;

import com.lulak.frugo.model.auth.RolePermission;
import com.lulak.frugo.model.auth.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {

    boolean existsByRole_IdAndPermission_Id(
            Integer roleId,
            Integer permissionId
    );

    void deleteByRole_IdAndPermission_Id(
            Integer roleId,
            Integer permissionId
    );
}
