package com.lulak.frugo.repository.pallet;

import com.lulak.frugo.dto.pallet.AdminPalletListDto;
import com.lulak.frugo.model.pallet.Pallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
}
