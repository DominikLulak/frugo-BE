package com.lulak.frugo.repository.employee;

import com.lulak.frugo.dto.employee.AdminEmployeeDetailDto;
import com.lulak.frugo.dto.employee.AdminEmployeeListDto;
import com.lulak.frugo.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>  {

    @Query("""
        SELECT new com.lulak.frugo.dto.employee.AdminEmployeeListDto(
            e.id,
            e.employeeNumber,
            CONCAT(e.firstName, ' ', e.lastName),
            s.code,
            d.name,
            jp.name,
            e.active    
        )
        FROM Employee e
        JOIN e.shift s
        JOIN e.jobPosition jp
        JOIN jp.department d
        WHERE
            (
                COALESCE(:employeeNumber, '') = ''
                OR LOWER(e.employeeNumber)
                    LIKE LOWER(CONCAT('%', :employeeNumber, '%'))
            )
        
        AND (
            COALESCE(:name, '') = ''
            OR LOWER(CONCAT(e.firstName, ' ', e.lastName))
                LIKE LOWER(CONCAT('%', :name, '%')) 
        )
        
        AND (
            COALESCE(:shiftCode, '') = ''
            OR LOWER(s.code)
                LIKE LOWER(CONCAT('%', :shiftCode, '%')) 
        )
        
        AND(
            COALESCE(:departmentName, '') = ''
            OR LOWER(d.name)
                LIKE LOWER(CONCAT('%', :departmentName, '%')) 
        )
        
        AND(
            COALESCE(:jobPositionName, '') = ''
            OR LOWER(jp.name)
                LIKE LOWER(CONCAT('%', :jobPositionName, '%')) 
        )
        
        AND(
            :isActive IS NULL 
            OR e.active = :isActive
        )
    """)
    List<AdminEmployeeListDto> getFilteredEmployees(
            @Param("employeeNumber") String employeeNumber,
            @Param("name") String name,
            @Param("shiftCode") String shiftCode,
            @Param("departmentName") String departmentName,
            @Param("jobPositionName") String jobPositionName,
            @Param("isActive") Boolean isActive
    );

    @Query("""
        SELECT new com.lulak.frugo.dto.employee.AdminEmployeeDetailDto(
            e.employeeNumber,
            CONCAT(e.firstName, ' ', e.lastName),
            e.address,
            e.city,
            e.postalCode,
            e.birthDate,
            e.hireDate,
            e.phone,
            e.email,
            e.systemUsername,
            s.code,
            d.name,
            jp.name,
            e.active,
            e.terminationDate,
            el.username 
        )
        FROM Employee e
        JOIN e.shift s
        JOIN e.jobPosition jp
        JOIN jp.department d
        LEFT JOIN EmployeeLogin el ON el.employee.id = e.id
        WHERE e.id = :id
    """)
    Optional<AdminEmployeeDetailDto> findEmployeeById(
            @Param("id") Integer id
    );
}
