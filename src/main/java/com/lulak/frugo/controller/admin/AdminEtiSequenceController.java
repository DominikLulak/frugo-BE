package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.referenceData.AdminEtiSequenceDto;
import com.lulak.frugo.service.referenceData.AdminEtiSequenceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/etiSeq")
public class AdminEtiSequenceController {

    private final AdminEtiSequenceService etiSequenceService;

    public AdminEtiSequenceController(
            AdminEtiSequenceService etiSequenceService
    ){
        this.etiSequenceService = etiSequenceService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminEtiSequenceDto> getAllSequences(){ return etiSequenceService.getAllSequences(); }
}
