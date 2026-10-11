package com.lulak.frugo.repository.employee;

import com.lulak.frugo.dto.employee.AdminEmployeeDetailDto;
import com.lulak.frugo.model.employee.EmployeeLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeLoginRepository extends JpaRepository<EmployeeLogin, Integer> {

    Optional<EmployeeLogin> findByUsername(String username);

    Optional<EmployeeLogin> findEmployeeLoginByEmployeeId(
            @Param("id") Integer id
    );
}
