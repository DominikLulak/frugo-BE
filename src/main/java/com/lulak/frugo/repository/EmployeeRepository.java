package com.lulak.frugo.repository;

import com.lulak.frugo.dto.AdminEmployeeListDto;
import com.lulak.frugo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>  {

    @Query("""
        SELECT new com.lulak.frugo.dto.AdminEmployeeListDto(
            e.personalNumber,
            e.firstName,
            e.lastName,
            e.position,
            e.phoneNumber,
            e.shift
        )
        FROM Employee e
        WHERE (:personalNumber IS NULL OR e.personalNumber LIKE %:personalNumber%)
        AND (:fullName IS NULL OR LOWER(CONCAT(e.firstName, ' ', e.lastName)) LIKE LOWER(CONCAT('%', :fullName, '%')))
        AND (:position IS NULL OR LOWER(e.position) LIKE LOWER(CONCAT('%', :position, '%')))
        AND (:phoneNumber IS NULL OR e.phoneNumber LIKE %:phoneNumber%)
        AND (:shift IS NULL OR e.shift LIKE %:shift%)
    """)
    List<AdminEmployeeListDto> getFilteredEmployees(
            String personalNumber,
            String fullName,
            String position,
            String phoneNumber,
            String shift
    );

    Optional<Employee> findByPersonalNumber(String personalNumber);

}
