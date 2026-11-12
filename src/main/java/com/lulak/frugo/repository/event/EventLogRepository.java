package com.lulak.frugo.repository.event;

import com.lulak.frugo.dto.event.AdminEventLogListDto;
import com.lulak.frugo.model.event.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

    @Query("""
        SELECT new com.lulak.frugo.dto.event.AdminEventLogListDto(
            el.id,
            el.reference,
            eld.code,
            el.startedAt,
            el.finishedAt,
            el.status,
            CONCAT(e.firstName, ' ', e.lastName),
            el.description 
        )
        FROM EventLog el
        JOIN el.eventLogDefinition eld
        LEFT JOIN el.employee e
        ORDER BY el.id DESC 
    """)
    List<AdminEventLogListDto> getAllEventLogs();
}
