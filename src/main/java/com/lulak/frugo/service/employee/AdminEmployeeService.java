package com.lulak.frugo.service.employee;

import com.lulak.frugo.dto.employee.AdminEmployeeDetailDto;
import com.lulak.frugo.dto.employee.AdminEmployeeListDto;
import com.lulak.frugo.repository.employee.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminEmployeeService {

    private final EmployeeRepository employeeRepository;

    public AdminEmployeeService(
            EmployeeRepository employeeRepository
    ){
        this.employeeRepository = employeeRepository;
    }

    public List<AdminEmployeeListDto> getFilteredEmployees(
            String employeeNumber,
            String name,
            String shiftCode,
            String departmentName,
            String jobPositionName,
            Boolean isActive
    ){
        return employeeRepository.getFilteredEmployees(
                employeeNumber,
                name,
                shiftCode,
                departmentName,
                jobPositionName,
                isActive
        );
    }

    public AdminEmployeeDetailDto getEmployeeDetail(Integer id){
        return employeeRepository.findEmployeeById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found: " + id)
                );
    }
}
