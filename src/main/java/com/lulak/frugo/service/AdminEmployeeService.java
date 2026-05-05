package com.lulak.frugo.service;

import com.lulak.frugo.dto.AdminEmployeeDetailDto;
import com.lulak.frugo.dto.AdminEmployeeListDto;
import com.lulak.frugo.model.Employee;
import com.lulak.frugo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminEmployeeService {

    private final EmployeeRepository employeeRepository;

    public AdminEmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<AdminEmployeeListDto> getFilteredEmployees(
            String personalNumber,
            String fullName,
            String position,
            String phoneNumber,
            String shift
    ){
        return employeeRepository.getFilteredEmployees(
                personalNumber,
                fullName,
                position,
                phoneNumber,
                shift
        );
    }

    public AdminEmployeeDetailDto getEmployeeDetail(String personalNumber){
        Employee e = employeeRepository.findByPersonalNumber(personalNumber)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return new AdminEmployeeDetailDto(
                e.getPersonalNumber(),
                e.getFirstName(),
                e.getLastName(),
                e.getPosition(),
                e.getPhoneNumber(),
                e.getEmail(),
                e.getAddress(),
                e.getShift(),
                e.getHireDate(),
                e.getBirthDate()
        );
    }
}
