package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.employee.AdminEmployeeDetailDto;
import com.lulak.frugo.dto.employee.AdminEmployeeListDto;
import com.lulak.frugo.service.employee.AdminEmployeeService;
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
    public List<AdminEmployeeListDto> getEmployees(
            @RequestParam(required = false) String employeeNumber,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String jobPosition,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String shift
    ){
        return adminEmployeeService.getFilteredEmployees(
                employeeNumber,
                fullName,
                jobPosition,
                phone,
                shift
        );
    }

    @GetMapping("/{employeeNumber}")
    public AdminEmployeeDetailDto getEmployeeDetail(
            @PathVariable String employeeNumber
    ){
        return adminEmployeeService.getEmployeeDetail(employeeNumber);
    }
}
