package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.referenceData.AdminShiftDto;
import com.lulak.frugo.service.referenceData.AdminShiftService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/shifts")
public class AdminShiftController {

    private final AdminShiftService adminShiftService;

    public AdminShiftController(
            AdminShiftService adminShiftService
    ){
        this.adminShiftService = adminShiftService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    public List<AdminShiftDto> getShifts(){ return adminShiftService.getAllShifts(); }
}
