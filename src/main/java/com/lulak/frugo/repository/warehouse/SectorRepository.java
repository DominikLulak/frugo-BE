package com.lulak.frugo.repository.warehouse;

import com.lulak.frugo.model.warehouse.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepository extends JpaRepository<Sector, Integer> {
    boolean existsByCodeAndWarehouseId(String code, Integer warehouseId);
    boolean existsByNameAndWarehouseId(String name, Integer warehouseId);

    boolean existsByCodeAndWarehouseIdAndIdNot(
            String code,
            Integer warehouseId,
            Integer id
    );
    boolean existsByNameAndWarehouseIdAndIdNot(
            String name,
            Integer warehouseId,
            Integer id
    );
}
