package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.employee.AdminEmployeeDetailDto;
import com.lulak.frugo.dto.employee.AdminEmployeeListDto;
import com.lulak.frugo.service.employee.AdminEmployeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/employees")
@CrossOrigin("*")
public class AdminEmployeeController {

    private final AdminEmployeeService adminEmployeeService;

    public AdminEmployeeController(AdminEmployeeService adminEmployeeService) {
        this.adminEmployeeService = adminEmployeeService;
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
}
