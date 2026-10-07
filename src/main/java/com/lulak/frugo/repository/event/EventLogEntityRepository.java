package com.lulak.frugo.repository.event;

import com.lulak.frugo.model.event.EventLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventLogEntityRepository extends JpaRepository<EventLogEntity, Integer> {

    List<EventLogEntity> findByEventLogId(Integer eventLogId);
}
