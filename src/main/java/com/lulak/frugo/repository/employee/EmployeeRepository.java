package com.lulak.frugo.repository.employee;

import com.lulak.frugo.dto.employee.AdminEmployeeListDto;
import com.lulak.frugo.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>  {

    @Query("""
        SELECT new com.lulak.frugo.dto.employee.AdminEmployeeListDto(
            e.employeeNumber,
            e.firstName,
            e.lastName,
            e.jobPosition.name,
            e.phone,
            e.shift.code
        )
        FROM Employee e
        WHERE (:employeeNumber IS NULL
                OR e.employeeNumber LIKE %:employeeNumber%)
                
        AND (:fullName IS NULL
                OR LOWER(CONCAT(e.firstName, ' ', e.lastName))
                    LIKE LOWER(CONCAT('%', :fullName, '%')))
        
        AND (:jobPosition IS NULL 
                OR LOWER(e.jobPosition.name)
                    LIKE LOWER(CONCAT('%', :jobPosition, '%')))
        
        AND (:phone IS NULL 
                OR e.phone LIKE %:phone%)
        
        AND (:shift IS NULL 
                OR e.shift.code LIKE %:shift%)
    """)
    List<AdminEmployeeListDto> getFilteredEmployees(
            String employeeNumber,
            String fullName,
            String jobPosition,
            String phone,
            String shift
    );

    Optional<Employee> findByEmployeeNumber(String employeeNumber);
}
