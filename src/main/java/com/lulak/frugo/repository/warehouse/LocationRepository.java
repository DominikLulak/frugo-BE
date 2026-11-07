package com.lulak.frugo.repository.warehouse;

import com.lulak.frugo.model.warehouse.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
