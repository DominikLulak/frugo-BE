package com.lulak.frugo.repository.referenceData;

import com.lulak.frugo.dto.referenceData.AdminShiftDto;
import com.lulak.frugo.model.employee.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.referenceData.AdminShiftDto(
            s.id,
            s.code,
            s.description
        )
        FROM Shift s
        ORDER BY s.id
    """)
    List<AdminShiftDto> getAllShifts();
}
