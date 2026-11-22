package com.lulak.frugo.repository.pallet;

import com.lulak.frugo.dto.pallet.AdminPalletListDto;
import com.lulak.frugo.model.pallet.Pallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public interface PalletRepository extends JpaRepository<Pallet, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.pallet.AdminPalletListDto(
            p.id,
            p.palletNumber,
            l.code,
            p.closed
        )
        FROM Pallet p
        JOIN p.location l
        WHERE (
            COALESCE(:palletNumber, '') = ''
            OR p.palletNumber LIKE CONCAT('%', :palletNumber, '%') 
        )
        AND(
            COALESCE(:locationCode, '') = ''
            OR l.code LIKE CONCAT('%', :locationCode, '%') 
        )
        AND (
            :isClosed IS NULL 
            OR p.closed = :isClosed
        )
    """)
    List<AdminPalletListDto> getFilteredPallets(
            @Param("palletNumber") String palletNumber,
            @Param("locationCode") String locationCode,
            @Param("isClosed") Boolean isClosed
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT p
    FROM Pallet p
    WHERE p.palletNumber = :etiNumber
""")
    Optional<Pallet> findByEtiNumberForUpdate(
            @Param("etiNumber") String etiNumber
    );
}
