package com.lulak.frugo.repository.event;

import com.lulak.frugo.model.event.StockMovementDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockMovementDefinitionRepository extends JpaRepository<StockMovementDefinition, Integer> {

    Optional<StockMovementDefinition> findByCode(String code);
}
