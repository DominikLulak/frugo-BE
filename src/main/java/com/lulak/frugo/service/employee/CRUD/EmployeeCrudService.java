package com.lulak.frugo.service.employee.CRUD;

import com.lulak.frugo.dto.employee.CRUD.EmployeeCreateDto;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.employee.JobPosition;
import com.lulak.frugo.model.employee.Shift;
import com.lulak.frugo.repository.employee.EmployeeRepository;
import com.lulak.frugo.repository.employee.department.DepartmentRepository;
import com.lulak.frugo.repository.employee.department.JobPositionRepository;
import com.lulak.frugo.repository.referenceData.ShiftRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
public class EmployeeCrudService {

    private final ShiftRepository shiftRepository;
    private final JobPositionRepository jobPositionRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeCrudService(
            ShiftRepository shiftRepository,
            JobPositionRepository jobPositionRepository,
            EmployeeRepository employeeRepository
    ){
        this.shiftRepository = shiftRepository;
        this.jobPositionRepository = jobPositionRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Employee createEmployee(
            EmployeeCreateDto dto
    ){
        Shift shift = shiftRepository.findById(dto.getShiftId())
                .orElseThrow(() -> new RuntimeException("Shift not found " + dto.getShiftId()));

        JobPosition jobPosition = jobPositionRepository.findById(dto.getJobPositionId())
                .orElseThrow(() -> new RuntimeException("Job position not found " + dto.getJobPositionId()));


        Employee employee = new Employee();

        employee.setEmployeeNumber("TEMP-" + UUID.randomUUID());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setAddress(dto.getAddress());
        employee.setCity(dto.getCity());
        employee.setPostalCode(dto.getPostalCode());
        employee.setBirthDate(dto.getBirthDate());
        employee.setHireDate(LocalDate.now());
        employee.setShift(shift);
        employee.setJobPosition(jobPosition);
        employee.setActive(true);
        employee.setSystemUsername(dto.getSystemUsername());
        employee.setPhone(dto.getPhone());
        employee.setEmail(dto.getEmail());

        employee = employeeRepository.save(employee);

        String employeeNumber = String.format(
                "EMP%03d",
                employee.getId()
        );

        employee.setEmployeeNumber(employeeNumber);

        employeeRepository.save(employee);

        return employee;
    }

    @Transactional
    public Employee updateEmployee(
            Integer employeeId,
            EmployeeCreateDto dto
    ){
        Shift shift = shiftRepository.findById(dto.getShiftId())
                .orElseThrow(() -> new RuntimeException("Shift not found " + dto.getShiftId()));

        JobPosition jobPosition = jobPositionRepository.findById(dto.getJobPositionId())
                .orElseThrow(() -> new RuntimeException("Job position not found " + dto.getJobPositionId()));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found " + employeeId));

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setAddress(dto.getAddress());
        employee.setCity(dto.getCity());
        employee.setPostalCode(dto.getPostalCode());
        employee.setBirthDate(dto.getBirthDate());
        employee.setShift(shift);
        employee.setJobPosition(jobPosition);
        employee.setSystemUsername(dto.getSystemUsername());
        employee.setPhone(dto.getPhone());
        employee.setEmail(dto.getEmail());

        return employeeRepository.save(employee);
    }
}
