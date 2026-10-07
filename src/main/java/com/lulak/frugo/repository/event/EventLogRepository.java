package com.lulak.frugo.repository.event;

import com.lulak.frugo.dto.event.AdminEventLogListDto;
import com.lulak.frugo.model.event.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventLogRepository extends JpaRepository<EventLog, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.event.AdminEventLogListDto(
            el.id,
            ed.code,
            el.createdAt,
            CONCAT(e.firstName, ' ', e.lastName),
            el.description 
        )
        FROM EventLog el
        JOIN el.eventDefinition ed
        LEFT JOIN el.employee e
        ORDER BY el.createdAt DESC 
    """)
    List<AdminEventLogListDto> getAllEventLogs();
}
