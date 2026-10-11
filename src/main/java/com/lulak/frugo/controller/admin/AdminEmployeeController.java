package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.employee.AdminEmployeeDetailDto;
import com.lulak.frugo.dto.employee.AdminEmployeeListDto;
import com.lulak.frugo.dto.employee.CRUD.EmployeeCreateDto;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.employee.EmployeeLogin;
import com.lulak.frugo.service.employee.AdminEmployeeService;
import com.lulak.frugo.service.employee.CRUD.EmployeeCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/employees")
@CrossOrigin("*")
public class AdminEmployeeController {

    private final AdminEmployeeService adminEmployeeService;
    private final EmployeeCrudService employeeCrudService;

    public AdminEmployeeController(
            AdminEmployeeService adminEmployeeService,
            EmployeeCrudService employeeCrudService
    ) {
        this.adminEmployeeService = adminEmployeeService;
        this.employeeCrudService = employeeCrudService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    public List<AdminEmployeeListDto> getEmployees(
            @RequestParam(required = false) String employeeNumber,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String shiftCode,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) String jobPositionName,
            @RequestParam(required = false) Boolean isActive
    ){
        return adminEmployeeService.getFilteredEmployees(
                employeeNumber,
                name,
                shiftCode,
                departmentName,
                jobPositionName,
                isActive
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    public AdminEmployeeDetailDto getEmployeeDetail(
            @PathVariable Integer id
    ){
        return adminEmployeeService.getEmployeeDetail(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    public ResponseEntity<Employee> createEmployee(
            @Valid @RequestBody EmployeeCreateDto dto
    ){
        Employee employee = employeeCrudService.createEmployee(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeeCreateDto dto
    ){
        Employee employee = employeeCrudService.updateEmployee(id, dto);

        return ResponseEntity.ok(employee);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    public ResponseEntity<EmployeeLogin> createEmployeeLogin(
            @PathVariable Integer id
    ){
        EmployeeLogin employeeLogin = employeeCrudService.createLogin(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(employeeLogin);
    }
}
