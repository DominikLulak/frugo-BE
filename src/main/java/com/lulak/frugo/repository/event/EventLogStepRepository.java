package com.lulak.frugo.repository.event;

import com.lulak.frugo.model.event.EventLogStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventLogStepRepository extends JpaRepository<EventLogStep, Long> {

    List<EventLogStep> findByEventLogIdOrderByStepNumberAsc(Long eventLogId);

    Optional<EventLogStep> findFirstByEventLogIdOrderByStepNumberDesc(Long eventLogId);
}
