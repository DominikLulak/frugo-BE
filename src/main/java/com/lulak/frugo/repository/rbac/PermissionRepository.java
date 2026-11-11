package com.lulak.frugo.repository.rbac;

import com.lulak.frugo.model.auth.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    boolean existsByCode(String code);

    boolean existsByName(String code);

    boolean existsByCodeAndIdNot(String code, Integer permissionId);

    boolean existsByNameAndIdNot(String code, Integer permissionId);
}
