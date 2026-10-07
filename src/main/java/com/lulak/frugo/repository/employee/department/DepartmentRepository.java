package com.lulak.frugo.repository.employee.department;

import com.lulak.frugo.dto.employee.AdminEmployeeListDto;
import com.lulak.frugo.dto.employee.department.AdminDepartmentListDto;
import com.lulak.frugo.dto.employee.department.AdminJobPositionListDto;
import com.lulak.frugo.model.employee.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.employee.department.AdminDepartmentListDto(
            d.id,
            d.code,
            d.name,
            d.description
        )
        FROM Department d
        ORDER BY d.id
    """)
    List<AdminDepartmentListDto> getAllDepartments();

    @Query("""
        SELECT new com.lulak.frugo.dto.employee.department.AdminJobPositionListDto(
            jp.id,
            d.name,
            jp.code,
            jp.name,
            jp.description
        )
        FROM JobPosition jp
        JOIN jp.department d
        WHERE jp.department.id = :departmentId
        ORDER BY jp.id
    """)
    List<AdminJobPositionListDto> getDepartmentsJobPositions(
            @Param("departmentId") Integer departmentId
    );

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
        WHERE e.jobPosition.id = :jobPositionId
        ORDER BY e.id
    """)
    List<AdminEmployeeListDto> getJobPositionEmployees(
            @Param("jobPositionId") Integer jobPositionId
    );
}
