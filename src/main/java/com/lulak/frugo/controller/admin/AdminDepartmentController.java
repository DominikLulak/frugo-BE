package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.employee.department.AdminDepartmentDetailDto;
import com.lulak.frugo.dto.employee.department.AdminDepartmentListDto;
import com.lulak.frugo.dto.employee.department.AdminJobPositionDetailDto;
import com.lulak.frugo.service.employee.department.AdminDepartmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/departments")
public class AdminDepartmentController {

    private final AdminDepartmentService adminDepartmentService;

    public AdminDepartmentController(
            AdminDepartmentService adminDepartmentService
    ){
        this.adminDepartmentService = adminDepartmentService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminDepartmentListDto> getDepartments(){ return adminDepartmentService.getAllDepartments(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminDepartmentDetailDto getDepartmentDetail(
            @PathVariable Integer id
    ){
        return adminDepartmentService.getDepartmentDetail(id);
    }

    @GetMapping("/jobPosition/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminJobPositionDetailDto getJobPosition(
            @PathVariable Integer id
    ){
        return adminDepartmentService.getJobPositionDetail(id);
    }
}
