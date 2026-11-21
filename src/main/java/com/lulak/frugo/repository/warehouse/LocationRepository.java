package com.lulak.frugo.repository.warehouse;

import com.lulak.frugo.model.warehouse.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    List<Location> findAllByCodeAndSector_CodeAndSector_Warehouse_Code(
            String locationCode,
            String sectorCode,
            String warehouseCode
    );
}
