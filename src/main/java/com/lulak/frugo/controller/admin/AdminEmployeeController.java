package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.AdminEmployeeDetailDto;
import com.lulak.frugo.dto.AdminEmployeeListDto;
import com.lulak.frugo.service.AdminEmployeeService;
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
            @RequestParam(required = false) String personalNumber,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String shift
    ){
        return adminEmployeeService.getFilteredEmployees(
                personalNumber,
                fullName,
                position,
                phoneNumber,
                shift
        );
    }

    @GetMapping("/{personalNumber}")
    public AdminEmployeeDetailDto getEmployeeDetail(
            @PathVariable String personalNumber
    ){
        return adminEmployeeService.getEmployeeDetail(personalNumber);
    }
}
