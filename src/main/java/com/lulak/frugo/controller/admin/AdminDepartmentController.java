package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.employee.department.AdminDepartmentDetailDto;
import com.lulak.frugo.dto.employee.department.AdminDepartmentListDto;
import com.lulak.frugo.dto.employee.department.AdminJobPositionDetailDto;
import com.lulak.frugo.dto.employee.department.CRUD.DepartmentCrudDto;
import com.lulak.frugo.dto.employee.department.CRUD.JobPositionCrudDto;
import com.lulak.frugo.model.employee.Department;
import com.lulak.frugo.model.employee.JobPosition;
import com.lulak.frugo.service.employee.CRUD.DepartmentCrudService;
import com.lulak.frugo.service.employee.department.AdminDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/departments")
public class AdminDepartmentController {

    private final AdminDepartmentService adminDepartmentService;
    private final DepartmentCrudService departmentCrudService;

    public AdminDepartmentController(
            AdminDepartmentService adminDepartmentService,
            DepartmentCrudService departmentCrudService
    ){
        this.adminDepartmentService = adminDepartmentService;
        this.departmentCrudService = departmentCrudService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('DEPARTMENTS_READ')")
    public List<AdminDepartmentListDto> getDepartments(){ return adminDepartmentService.getAllDepartments(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DEPARTMENTS_READ')")
    public AdminDepartmentDetailDto getDepartmentDetail(
            @PathVariable Integer id
    ){
        return adminDepartmentService.getDepartmentDetail(id);
    }

    @GetMapping("/jobPosition/{id}")
    @PreAuthorize("hasAuthority('DEPARTMENTS_READ')")
    public AdminJobPositionDetailDto getJobPosition(
            @PathVariable Integer id
    ){
        return adminDepartmentService.getJobPositionDetail(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('DEPARTMENTS_CREATE')")
    public ResponseEntity<Department> createDepartment(
            @Valid @RequestBody DepartmentCrudDto dto
    ){
        Department department = departmentCrudService.createDepartment(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DEPARTMENTS_EDIT')")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable Integer id,
            @Valid @RequestBody DepartmentCrudDto dto
    ){
        Department department = departmentCrudService.updateDepartment(id, dto);

        return ResponseEntity.ok(department);
    }

    @PostMapping("/{departmentId}/jobPosition")
    @PreAuthorize("hasAuthority('DEPARTMENTS_EDIT')")
    public ResponseEntity<JobPosition> createJobPosition(
            @PathVariable Integer departmentId,
            @Valid @RequestBody JobPositionCrudDto dto
    ){
        JobPosition jobPosition = departmentCrudService.createJobPosition(departmentId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(jobPosition);
    }

    @PutMapping("/{departmentId}/jobPosition/{jobPositionId}")
    @PreAuthorize("hasAuthority('DEPARTMENTS_EDIT')")
    public ResponseEntity<JobPosition> updateJobPosition(
            @PathVariable Integer departmentId,
            @PathVariable Integer jobPositionId,
            @Valid @RequestBody JobPositionCrudDto dto
    ){
        JobPosition jobPosition = departmentCrudService.updateJobPosition(departmentId, jobPositionId, dto);

        return ResponseEntity.ok(jobPosition);
    }
}
