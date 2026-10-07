package com.lulak.frugo.repository.warehouse;

import com.lulak.frugo.dto.warehouse.AdminWarehouseListDto;
import com.lulak.frugo.dto.warehouse.AdminWarehouseLocationListDto;
import com.lulak.frugo.dto.warehouse.AdminWarehouseSectorDetailDto;
import com.lulak.frugo.dto.warehouse.AdminWarehouseSectorListDto;
import com.lulak.frugo.model.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.warehouse.AdminWarehouseListDto(
            w.id,
            w.code,
            w.name,
            w.description
        )
        FROM Warehouse w
        ORDER BY w.id
    """)
    List<AdminWarehouseListDto> getAllWarehouses();

    @Query("""
        SELECT new com.lulak.frugo.dto.warehouse.AdminWarehouseSectorListDto(
            s.id,
            s.code,
            s.name,
            st.code,
            st.name,
            s.description
        )
        FROM Sector s
        JOIN s.type st
        WHERE s.warehouse.id = :warehouseId
        ORDER BY s.id
    """)
    List<AdminWarehouseSectorListDto> getWarehouseSectors(
            @Param("warehouseId") Integer warehouseId
    );

    @Query("""
        SELECT new com.lulak.frugo.dto.warehouse.AdminWarehouseLocationListDto(
            l.id,
            l.code,
            l.aisle,
            l.rack,
            l.level,
            l.position,
            l.canBeOrdered
        )
        FROM Location l
        WHERE l.sector.id = :sectorId
        ORDER BY l.id
    """)
    List<AdminWarehouseLocationListDto> getSectorLocations(
            @Param("sectorId") Integer sectorId
    );
}
