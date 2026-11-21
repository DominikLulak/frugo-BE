
package com.lulak.frugo.service.event;

import com.lulak.frugo.model.employee.Employee;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EventLogTrackingService {

    private final EventLogPersistenceService persistenceService;

    public EventLogTrackingService(
            EventLogPersistenceService persistenceService
    ) {
        this.persistenceService = persistenceService;
    }

    public Long startEvent(
            String eventCode,
            Employee employee,
            String reference,
            String description,
            Map<String, Object> data
    ) {
        return persistenceService.startEvent(
                eventCode,
                employee,
                reference,
                description,
                data
        );
    }

    public Long startStep(
            Long eventLogId,
            String name,
            String description,
            Map<String, Object> data
    ) {
        return persistenceService.startStep(
                eventLogId,
                name,
                description,
                data
        );
    }

    public void completeStep(Long stepId) {
        persistenceService.completeStep(stepId);
    }

    public void failStep(
            Long stepId,
            String errorCode,
            String errorMessage
    ) {
        persistenceService.failStep(
                stepId,
                errorCode,
                errorMessage
        );
    }

    public void addEntity(
            Long eventLogId,
            String entityType,
            Integer entityId
    ) {
        persistenceService.addEntity(
                eventLogId,
                entityType,
                entityId
        );
    }

    public void completeEvent(Long eventLogId) {
        persistenceService.completeEvent(eventLogId);
    }

    public void completeEventWithPartialSuccess(Long eventLogId) {
        persistenceService.completeEventWithPartialSuccess(eventLogId);
    }

    public void failEvent(Long eventLogId) {
        persistenceService.failEvent(eventLogId);
    }
}
