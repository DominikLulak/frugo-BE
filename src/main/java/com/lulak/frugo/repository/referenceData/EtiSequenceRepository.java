package com.lulak.frugo.repository.referenceData;

import com.lulak.frugo.dto.referenceData.AdminEtiSequenceDto;
import com.lulak.frugo.model.EtiSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EtiSequenceRepository extends JpaRepository<EtiSequence, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.referenceData.AdminEtiSequenceDto(
            esq.id,
            esq.code,
            esq.description,
            esq.lastNumber
        )
        FROM EtiSequence esq
    """)
    List<AdminEtiSequenceDto> getAllSequences();
}
