package com.lulak.frugo.repository.referenceData;

import com.lulak.frugo.dto.referenceData.AdminEtiSequenceDto;
import com.lulak.frugo.model.EtiSequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT esq
        FROM EtiSequence esq
        WHERE esq.code = :code
    """)
    Optional<EtiSequence> findByCodeForUpdate(@Param("code") String code);
}
