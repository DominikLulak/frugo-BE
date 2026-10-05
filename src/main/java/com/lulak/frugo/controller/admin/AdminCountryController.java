package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.referenceData.AdminCountryDto;
import com.lulak.frugo.service.referenceData.AdminCountryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/countries")
public class AdminCountryController {

    private final AdminCountryService adminCountryService;

    public AdminCountryController(
            AdminCountryService adminCountryService
    ){
        this.adminCountryService = adminCountryService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    public List<AdminCountryDto> getCountries(){ return adminCountryService.getAllCountries(); }
}
