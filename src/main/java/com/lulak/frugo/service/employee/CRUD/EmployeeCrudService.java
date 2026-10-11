package com.lulak.frugo.service.employee.CRUD;

import com.lulak.frugo.dto.employee.CRUD.EmployeeCreateDto;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.employee.EmployeeLogin;
import com.lulak.frugo.model.employee.JobPosition;
import com.lulak.frugo.model.employee.Shift;
import com.lulak.frugo.repository.employee.EmployeeLoginRepository;
import com.lulak.frugo.repository.employee.EmployeeRepository;
import com.lulak.frugo.repository.employee.department.JobPositionRepository;
import com.lulak.frugo.repository.referenceData.ShiftRepository;
import com.lulak.frugo.security.PasswordService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Service
public class EmployeeCrudService {

    private final ShiftRepository shiftRepository;
    private final JobPositionRepository jobPositionRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeLoginRepository employeeLoginRepository;
    private final PasswordService passwordService;

    private String removeDiacritics(String value){
        return Normalizer
                .normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }

    public EmployeeCrudService(
            ShiftRepository shiftRepository,
            JobPositionRepository jobPositionRepository,
            EmployeeRepository employeeRepository,
            EmployeeLoginRepository employeeLoginRepository,
            PasswordService passwordService
    ){
        this.shiftRepository = shiftRepository;
        this.jobPositionRepository = jobPositionRepository;
        this.employeeRepository = employeeRepository;
        this.employeeLoginRepository = employeeLoginRepository;
        this.passwordService = passwordService;
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

    @Transactional
    public EmployeeLogin createLogin(
            Integer employeeId
    ){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found " + employeeId));

        EmployeeLogin employeeLogin = new EmployeeLogin();

        String username = removeDiacritics(employee.getLastName().toUpperCase(Locale.ROOT) + "_" + employee.getFirstName().toUpperCase(Locale.ROOT));
        String hashedPassword = passwordService.hashPassword("_FRUGO" + employee.getEmployeeNumber() + "_");

        employeeLogin.setEmployee(employee);
        employeeLogin.setUsername(username);
        employeeLogin.setPasswordHash(hashedPassword);

        employeeLoginRepository.save(employeeLogin);

        return employeeLogin;
    }

    @Transactional
    public void deleteLogin(
            Integer employeeId
    ){
        EmployeeLogin employeeLogin = employeeLoginRepository.findEmployeeLoginByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee login not found."));

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        EmployeeLogin loginOfActiveUser = employeeLoginRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Login not found"));

        if (Objects.equals(
                employeeLogin.getEmployeeId(),
                loginOfActiveUser.getEmployeeId()
        )) {
            throw new RuntimeException("Cannot delete own account");
        }

        employeeLoginRepository.delete(employeeLogin);
    }

    @Transactional
    public Employee setEmployeeActive(
            Integer employeeId,
            boolean active
    ){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found " + employeeId));

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        EmployeeLogin loginOfActiveUser = employeeLoginRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Login not found"));

        if (employeeLoginRepository
                .findEmployeeLoginByEmployeeId(employeeId)
                .map(login -> Objects.equals(
                        login.getEmployeeId(),
                        loginOfActiveUser.getEmployeeId()
                ))
                .orElse(false)) {
            throw new RuntimeException("Cannot edit status of own account");
        }

        employee.setActive(active);

        if(!active){
            employeeLoginRepository
                    .findEmployeeLoginByEmployeeId(employeeId)
                    .ifPresent(employeeLoginRepository::delete);

            employee.setTerminationDate(LocalDate.now());
        }else{
            employee.setTerminationDate(null);
        }

        return employeeRepository.save(employee);

    }
}
