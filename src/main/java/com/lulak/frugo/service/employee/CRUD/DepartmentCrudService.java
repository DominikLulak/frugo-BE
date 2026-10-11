package com.lulak.frugo.service.employee.CRUD;

import com.lulak.frugo.dto.employee.department.CRUD.DepartmentCrudDto;
import com.lulak.frugo.dto.employee.department.CRUD.JobPositionCrudDto;
import com.lulak.frugo.model.employee.Department;
import com.lulak.frugo.model.employee.JobPosition;
import com.lulak.frugo.repository.employee.department.DepartmentRepository;
import com.lulak.frugo.repository.employee.department.JobPositionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DepartmentCrudService {

    private final DepartmentRepository departmentRepository;
    private final JobPositionRepository jobPositionRepository;

    public DepartmentCrudService(
            DepartmentRepository departmentRepository,
            JobPositionRepository jobPositionRepository
    ){
        this.departmentRepository = departmentRepository;
        this.jobPositionRepository = jobPositionRepository;
    }

    @Transactional
    public Department createDepartment(
            DepartmentCrudDto dto
    ){
        if(departmentRepository.existsByName(dto.getName())){
            throw new RuntimeException("Department name already exists.");
        }
        if(departmentRepository.existsByCode(dto.getCode())){
            throw new RuntimeException("Department code already exists.");
        }

        Department department = new Department();

        department.setName(dto.getName());
        department.setCode(dto.getCode());
        department.setDescription(dto.getDescription());

        department = departmentRepository.save(department);
        return department;
    }

    @Transactional
    public Department updateDepartment(
            Integer departmentId,
            DepartmentCrudDto dto
    ){
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found " + departmentId));

        if(!department.getName().equals(dto.getName())
            && departmentRepository.existsByNameAndIdNot(dto.getName(), departmentId)){
            throw new RuntimeException("Department name already exists");
        }
        if(!department.getCode().equals(dto.getCode())
            && departmentRepository.existsByCodeAndIdNot(dto.getCode(), departmentId)){
            throw new RuntimeException("Department code already exists");
        }

        department.setName(dto.getName());
        department.setCode(dto.getCode());
        department.setDescription(dto.getDescription());

        return departmentRepository.save(department);
    }

    @Transactional
    public JobPosition createJobPosition(
            Integer departmentId,
            JobPositionCrudDto dto
    ){
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found " + departmentId));

        if(jobPositionRepository.existsByName(dto.getName())){
            throw new RuntimeException("Job position name already exists");
        }
        if(jobPositionRepository.existsByCode(dto.getCode())){
            throw new RuntimeException("Job position code already exists");
        }

        JobPosition jobPosition = new JobPosition();

        jobPosition.setDepartment(department);
        jobPosition.setName(dto.getName());
        jobPosition.setCode(dto.getCode());
        jobPosition.setDescription(dto.getDescription());

        jobPosition = jobPositionRepository.save(jobPosition);
        return jobPosition;
    }

    @Transactional
    public JobPosition updateJobPosition(
            Integer departmentId,
            Integer jobPositionId,
            JobPositionCrudDto dto
    ){
        JobPosition jobPosition = jobPositionRepository.findById(jobPositionId)
                .orElseThrow(() -> new RuntimeException("Job position not found " + jobPositionId));

        if(!Objects.equals(
                jobPosition.getDepartment().getId(),
                departmentId
        )){
            throw new RuntimeException("Job position does not belong to department " + departmentId);
        }

        if(!jobPosition.getName().equals(dto.getName())
            && jobPositionRepository.existsByNameAndIdNot(dto.getName(), jobPositionId)){
            throw new RuntimeException("Job position name already exists");
        }
        if(!jobPosition.getCode().equals(dto.getCode())
                && jobPositionRepository.existsByCodeAndIdNot(dto.getCode(), jobPositionId)){
            throw new RuntimeException("Job position code already exists");
        }

        jobPosition.setName(dto.getName());
        jobPosition.setCode(dto.getCode());
        jobPosition.setDescription(dto.getDescription());

        return jobPositionRepository.save(jobPosition);
    }
}
