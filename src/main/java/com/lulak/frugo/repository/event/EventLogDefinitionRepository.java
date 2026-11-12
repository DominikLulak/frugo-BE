package com.lulak.frugo.repository.event;

import com.lulak.frugo.model.event.EventLogDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventLogDefinitionRepository extends JpaRepository<EventLogDefinition, Integer> {

    Optional<EventLogDefinition> findByCode(String code);
}
