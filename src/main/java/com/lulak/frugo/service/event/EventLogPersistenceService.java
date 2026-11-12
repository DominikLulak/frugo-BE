
package com.lulak.frugo.service.event;

import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.event.*;
import com.lulak.frugo.repository.event.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class EventLogPersistenceService {

    private final EventLogRepository eventLogRepository;
    private final EventLogDefinitionRepository definitionRepository;
    private final EventLogStepRepository stepRepository;
    private final EventLogEntityRepository entityRepository;
    private final ObjectMapper objectMapper;

    public EventLogPersistenceService(
            EventLogRepository eventLogRepository,
            EventLogDefinitionRepository definitionRepository,
            EventLogStepRepository stepRepository,
            EventLogEntityRepository entityRepository,
            ObjectMapper objectMapper
    ) {
        this.eventLogRepository = eventLogRepository;
        this.definitionRepository = definitionRepository;
        this.stepRepository = stepRepository;
        this.entityRepository = entityRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long startEvent(
            String eventCode,
            Employee employee,
            String reference,
            String description,
            Map<String, Object> data
    ) {
        EventLogDefinition definition = definitionRepository
                .findByCode(eventCode)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Event definition not found: " + eventCode
                ));

        EventLog eventLog = new EventLog();
        eventLog.setEventLogDefinition(definition);
        eventLog.setStartedAt(LocalDateTime.now());
        eventLog.setEmployee(employee);
        eventLog.setStatus(EventLogStatus.IN_PROGRESS);
        eventLog.setReference(reference);
        eventLog.setDescription(description);
        eventLog.setData(toJson(data));

        return eventLogRepository.save(eventLog).getId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long startStep(
            Long eventLogId,
            String name,
            String description,
            Map<String, Object> data
    ) {
        EventLog eventLog = getEventLog(eventLogId);
        requireEventInProgress(eventLog);

        Integer stepNumber = stepRepository
                .findFirstByEventLogIdOrderByStepNumberDesc(eventLogId)
                .map(step -> step.getStepNumber() + 1)
                .orElse(1);

        EventLogStep step = new EventLogStep();
        step.setEventLog(eventLog);
        step.setStepNumber(stepNumber);
        step.setCreatedAt(LocalDateTime.now());
        step.setName(name);
        step.setDescription(description);
        step.setStatus(EventLogStepStatus.IN_PROGRESS);
        step.setData(toJson(data));

        return stepRepository.save(step).getId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeStep(Long stepId) {
        EventLogStep step = getStep(stepId);
        requireStepInProgress(step);

        step.setStatus(EventLogStepStatus.SUCCESS);
        step.setFinishedAt(LocalDateTime.now());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void failStep(
            Long stepId,
            String errorCode,
            String errorMessage
    ) {
        EventLogStep step = getStep(stepId);
        requireStepInProgress(step);

        step.setStatus(EventLogStepStatus.FAILED);
        step.setFinishedAt(LocalDateTime.now());
        step.setErrorCode(errorCode);
        step.setErrorMessage(errorMessage);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addEntity(
            Long eventLogId,
            String entityType,
            Integer entityId
    ) {
        EventLog eventLog = getEventLog(eventLogId);
        requireEventInProgress(eventLog);

        EventLogEntity entity = new EventLogEntity();
        entity.setEventLog(eventLog);
        entity.setEntityType(entityType);
        entity.setEntityId(entityId);

        entityRepository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeEvent(Long eventLogId) {
        finishEvent(eventLogId, EventLogStatus.SUCCESS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeEventWithPartialSuccess(Long eventLogId) {
        finishEvent(eventLogId, EventLogStatus.PARTIAL_SUCCESS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void failEvent(Long eventLogId) {
        finishEvent(eventLogId, EventLogStatus.FAILED);
    }

    private void finishEvent(Long eventLogId, EventLogStatus status) {
        EventLog eventLog = getEventLog(eventLogId);
        requireEventInProgress(eventLog);

        eventLog.setStatus(status);
        eventLog.setFinishedAt(LocalDateTime.now());
    }

    private EventLog getEventLog(Long id) {
        return eventLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Event log not found: " + id
                ));
    }

    private EventLogStep getStep(Long id) {
        return stepRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Event log step not found: " + id
                ));
    }

    private void requireEventInProgress(EventLog eventLog) {
        if (eventLog.getStatus() != EventLogStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Event is not in progress: " + eventLog.getId()
            );
        }
    }

    private void requireStepInProgress(EventLogStep step) {
        if (step.getStatus() != EventLogStepStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Step is not in progress: " + step.getId()
            );
        }
    }

    private String toJson(Map<String, Object> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Failed to serialize event log data.", e
            );
        }
    }
}
