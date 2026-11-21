package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.event.AdminEventLogDetailDto;
import com.lulak.frugo.dto.event.AdminEventLogListDto;
import com.lulak.frugo.service.event.EventLogQueryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/eventLogs")
public class AdminEventLogController {

    private final EventLogQueryService eventLogQueryService;

    public AdminEventLogController(
            EventLogQueryService eventLogQueryService
    ){
        this.eventLogQueryService = eventLogQueryService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EVENTS_READ')")
    public List<AdminEventLogListDto> getEventLogs(){
        return eventLogQueryService.getAllEventLogs();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENTS_READ')")
    public AdminEventLogDetailDto getEventLogDetails(
            @PathVariable Long id
    ){
        return eventLogQueryService.getEventLogDetail(id);
    }
}
