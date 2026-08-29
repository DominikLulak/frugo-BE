package com.lulak.frugo.service.employee;

import com.lulak.frugo.dto.employee.AdminEmployeeDetailDto;
import com.lulak.frugo.dto.employee.AdminEmployeeListDto;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.repository.employee.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminEmployeeService {

    private final EmployeeRepository employeeRepository;

    public AdminEmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<AdminEmployeeListDto> getFilteredEmployees(
        String emplyeeNumber,
        String fullName,
        String jobPosition,
        String phone,
        String shift
    ){
        return employeeRepository.getFilteredEmployees(
                emplyeeNumber,
                fullName,
                jobPosition,
                phone,
                shift
        );
    }

    public AdminEmployeeDetailDto getEmployeeDetail(String employeeNumber){
        Employee e = employeeRepository.findByEmployeeNumber(employeeNumber)
                .orElseThrow(() -> new RuntimeException("Employee not found!"));

        return new AdminEmployeeDetailDto(
                e.getEmployeeNumber(),
                e.getFirstName(),
                e.getLastName(),
                e.getJobPosition().getName(),
                e.getPhone(),
                e.getEmail(),
                e.getAddress(),
                e.getShift().getCode(),
                e.getHireDate(),
                e.getBirthDate()
        );
    }
}
