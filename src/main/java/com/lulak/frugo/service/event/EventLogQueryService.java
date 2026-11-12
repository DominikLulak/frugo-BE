package com.lulak.frugo.service.event;

import com.lulak.frugo.dto.event.AdminEventLogDetailDto;
import com.lulak.frugo.dto.event.AdminEventLogEntityDto;
import com.lulak.frugo.dto.event.AdminEventLogListDto;
import com.lulak.frugo.dto.event.EventLogStepDto;
import com.lulak.frugo.model.event.EventLog;
import com.lulak.frugo.model.event.EventLogEntity;
import com.lulak.frugo.model.event.EventLogStep;
import com.lulak.frugo.repository.event.EventLogEntityRepository;
import com.lulak.frugo.repository.event.EventLogRepository;
import com.lulak.frugo.repository.event.EventLogStepRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
public class EventLogQueryService {

    private final EventLogRepository eventLogRepository;
    private final EventLogEntityRepository eventLogEntityRepository;
    private final EventLogStepRepository eventLogStepRepository;
    private final ObjectMapper objectMapper;

    public EventLogQueryService(
            EventLogRepository eventLogRepository,
            EventLogEntityRepository eventLogEntityRepository,
            EventLogStepRepository eventLogStepRepository,
            ObjectMapper objectMapper
    ){
        this.eventLogRepository = eventLogRepository;
        this.eventLogEntityRepository = eventLogEntityRepository;
        this.eventLogStepRepository = eventLogStepRepository;
        this.objectMapper = objectMapper;
    }

    public List<AdminEventLogListDto> getAllEventLogs(){ return eventLogRepository.getAllEventLogs(); }

    @Transactional(readOnly = true)
    public AdminEventLogDetailDto getEventLogDetail(Long id){

        EventLog eventLog = eventLogRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Event log not found with id: " + id));

        List<AdminEventLogEntityDto> entities =
                eventLogEntityRepository.findByEventLogId(id)
                        .stream()
                        .map(this::mapEntity)
                        .toList();

        List<EventLogStepDto> steps =
                eventLogStepRepository.findByEventLogIdOrderByStepNumberAsc(id)
                        .stream()
                        .map(this::mapStep)
                        .toList();

        String employeeNumber = null;
        String employeeName = null;

        if(eventLog.getEmployee() != null){
            employeeNumber = eventLog.getEmployee().getEmployeeNumber();
            employeeName = eventLog.getEmployee().getFirstName()
                    + " "
                    + eventLog.getEmployee().getLastName();
        }

        Map<String, Object> data = null;

        if(eventLog.getData() != null){
            try{
                data = objectMapper.readValue(
                        eventLog.getData(),
                        new TypeReference<Map<String, Object>>(){}
                );
            } catch (Exception e){
                throw new RuntimeException("Failed to parse event log data with id" + id, e);
            }
        }

        return new AdminEventLogDetailDto(
                eventLog.getId(),
                eventLog.getStartedAt(),
                eventLog.getFinishedAt(),
                eventLog.getStatus().name(),
                eventLog.getReference(),
                eventLog.getEventLogDefinition().getCode(),
                eventLog.getEventLogDefinition().getName(),
                eventLog.getEventLogDefinition().getDescription(),
                employeeNumber,
                employeeName,
                eventLog.getDescription(),
                data,
                entities,
                steps
        );
    }

    private AdminEventLogEntityDto mapEntity(EventLogEntity entity){
        return new AdminEventLogEntityDto(
                entity.getEntityType(),
                entity.getEntityId()
        );
    }

    private EventLogStepDto mapStep(EventLogStep step){
        Map<String, Object> data = null;

        if(step.getData() != null){
            try{
                data = objectMapper.readValue(
                        step.getData(),
                        new TypeReference<Map<String, Object>>() {}
                );
            }catch (Exception e){
                throw new RuntimeException(
                        "Failed to parse event log step data with id "
                        + step.getId(), e
                );
            }
        }

        return new EventLogStepDto(
                step.getId(),
                step.getStepNumber(),
                step.getCreatedAt(),
                step.getFinishedAt(),
                step.getName(),
                step.getDescription(),
                step.getStatus().name(),
                step.getErrorCode(),
                step.getErrorMessage(),
                data
        );
    }
}
