package com.lulak.frugo.repository.warehouse;

import com.lulak.frugo.dto.warehouse.AdminWarehouseSectorTypeDto;
import com.lulak.frugo.model.warehouse.SectorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SectorTypeRepository extends JpaRepository<SectorType, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.warehouse.AdminWarehouseSectorTypeDto(
            st.id,
            st.code,
            st.name,
            st.description
        )
        FROM SectorType st
        ORDER BY st.id
    """)
    List<AdminWarehouseSectorTypeDto> getAllSectorTypes();
}
