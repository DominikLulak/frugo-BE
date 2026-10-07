package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.referenceData.AdminStatusDto;
import com.lulak.frugo.service.referenceData.AdminStatusService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/statuses")
public class AdminStatusController {

    private final AdminStatusService adminStatusService;

    public AdminStatusController(
            AdminStatusService adminStatusService
    ){
        this.adminStatusService = adminStatusService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminStatusDto> getStatuses(){ return adminStatusService.getAllStatuses(); }
}
