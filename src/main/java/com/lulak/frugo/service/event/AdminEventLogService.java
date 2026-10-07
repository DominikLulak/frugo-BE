package com.lulak.frugo.service.event;

import com.lulak.frugo.dto.event.AdminEventLogDetailDto;
import com.lulak.frugo.dto.event.AdminEventLogEntityDto;
import com.lulak.frugo.dto.event.AdminEventLogListDto;
import com.lulak.frugo.model.event.EventLog;
import com.lulak.frugo.model.event.EventLogEntity;
import com.lulak.frugo.repository.event.EventLogEntityRepository;
import com.lulak.frugo.repository.event.EventLogRepository;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
public class AdminEventLogService {

    private final EventLogRepository eventLogRepository;
    private final EventLogEntityRepository eventLogEntityRepository;
    private final ObjectMapper objectMapper;

    public AdminEventLogService(
            EventLogRepository eventLogRepository,
            EventLogEntityRepository eventLogEntityRepository,
            ObjectMapper objectMapper
    ){
        this.eventLogRepository = eventLogRepository;
        this.eventLogEntityRepository = eventLogEntityRepository;
        this.objectMapper = objectMapper;
    }

    public List<AdminEventLogListDto> getAllEventLogs(){ return eventLogRepository.getAllEventLogs(); }

    public AdminEventLogDetailDto getEventLogDetail(Integer id){

        EventLog eventLog = eventLogRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Event log not found with id: " + id));

        List<AdminEventLogEntityDto> entities =
                eventLogEntityRepository.findByEventLogId(id)
                        .stream()
                        .map(this::mapEntity)
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
                throw new RuntimeException("Failed to parse event log data", e);
            }
        }

        return new AdminEventLogDetailDto(
                eventLog.getId(),
                eventLog.getCreatedAt(),
                eventLog.getEventDefinition().getCode(),
                eventLog.getEventDefinition().getName(),
                eventLog.getEventDefinition().getDescription(),
                employeeNumber,
                employeeName,
                eventLog.getDescription(),
                data,
                entities
        );
    }

    private AdminEventLogEntityDto mapEntity(EventLogEntity entity){
        return new AdminEventLogEntityDto(
                entity.getEntityType(),
                entity.getEntityId()
        );
    }
}
