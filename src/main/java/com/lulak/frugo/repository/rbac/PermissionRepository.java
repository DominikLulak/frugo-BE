package com.lulak.frugo.repository.rbac;

import com.lulak.frugo.model.auth.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
