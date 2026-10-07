package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.event.AdminEventLogDetailDto;
import com.lulak.frugo.dto.event.AdminEventLogListDto;
import com.lulak.frugo.service.event.AdminEventLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/eventLogs")
public class AdminEventLogController {

    private final AdminEventLogService eventLogService;

    public AdminEventLogController(
            AdminEventLogService eventLogService
    ){
        this.eventLogService = eventLogService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminEventLogListDto> getEventLogs(){
        return eventLogService.getAllEventLogs();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminEventLogDetailDto getEventLogDetails(
            @PathVariable Integer id
    ){
        return eventLogService.getEventLogDetail(id);
    }
}
