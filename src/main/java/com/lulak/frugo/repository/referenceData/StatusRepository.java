package com.lulak.frugo.repository.referenceData;

import com.lulak.frugo.dto.referenceData.AdminStatusDto;
import com.lulak.frugo.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.referenceData.AdminStatusDto(
            s.id,
            s.code,
            s.name,
            s.description
        )
        FROM Status s
        ORDER BY s.id
    """)
    List<AdminStatusDto> getAllStatuses();

    Optional<Status> findByCode(String code);
}
