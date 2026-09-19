package com.lulak.frugo.service.employee.department;

import com.lulak.frugo.dto.employee.AdminEmployeeListDto;
import com.lulak.frugo.dto.employee.department.AdminDepartmentDetailDto;
import com.lulak.frugo.dto.employee.department.AdminDepartmentListDto;
import com.lulak.frugo.dto.employee.department.AdminJobPositionDetailDto;
import com.lulak.frugo.dto.employee.department.AdminJobPositionListDto;
import com.lulak.frugo.model.employee.Department;
import com.lulak.frugo.model.employee.JobPosition;
import com.lulak.frugo.repository.employee.department.DepartmentRepository;
import com.lulak.frugo.repository.employee.department.JobPositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final JobPositionRepository jobPositionRepository;

    public AdminDepartmentService(
            DepartmentRepository departmentRepository,
            JobPositionRepository jobPositionRepository
    ){
        this.departmentRepository = departmentRepository;
        this.jobPositionRepository = jobPositionRepository;
    }

    public List<AdminDepartmentListDto> getAllDepartments(){ return departmentRepository.getAllDepartments(); }

    public AdminDepartmentDetailDto getDepartmentDetail(Integer id){

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found!"));

        List<AdminJobPositionListDto> jobPositions =
                departmentRepository.getDepartmentsJobPositions(id);

        return new AdminDepartmentDetailDto(
                department.getId(),
                department.getCode(),
                department.getName(),
                department.getDescription(),
                jobPositions
        );
    }

    public AdminJobPositionDetailDto getJobPositionDetail(Integer jobPositionId){
        JobPosition jobPosition = jobPositionRepository.findById(jobPositionId)
                .orElseThrow(() -> new RuntimeException("Job position not found!"));

        List<AdminEmployeeListDto> employees =
                departmentRepository.getJobPositionEmployees(jobPositionId);

        return new AdminJobPositionDetailDto(
                jobPosition.getId(),
                jobPosition.getDepartment().getName(),
                jobPosition.getCode(),
                jobPosition.getName(),
                jobPosition.getDescription(),
                employees
        );
    }
}
