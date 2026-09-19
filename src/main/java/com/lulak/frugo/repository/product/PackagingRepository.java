package com.lulak.frugo.repository.product;

import com.lulak.frugo.dto.product.AdminPackagingDto;
import com.lulak.frugo.model.product.Packaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackagingRepository extends JpaRepository<Packaging, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.product.AdminPackagingDto(
            p.id,
            p.code,
            p.name,
            p.description
        )
        FROM Packaging p
        ORDER BY p.id
    """)
    List<AdminPackagingDto> getAllPackaging();
}
