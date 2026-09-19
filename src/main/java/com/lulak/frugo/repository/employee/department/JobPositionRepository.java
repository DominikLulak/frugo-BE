package com.lulak.frugo.repository.employee.department;

import com.lulak.frugo.model.employee.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPositionRepository extends JpaRepository<JobPosition, Integer> {
}
