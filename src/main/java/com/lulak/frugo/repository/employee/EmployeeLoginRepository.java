package com.lulak.frugo.repository.employee;

import com.lulak.frugo.model.employee.EmployeeLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeLoginRepository extends JpaRepository<EmployeeLogin, Integer> {

    Optional<EmployeeLogin> findByUsername(String username);
}
