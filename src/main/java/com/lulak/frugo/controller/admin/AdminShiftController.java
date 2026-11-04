package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.referenceData.AdminShiftDto;
import com.lulak.frugo.dto.referenceData.CRUD.ShiftCreateDto;
import com.lulak.frugo.model.employee.Shift;
import com.lulak.frugo.service.referenceData.AdminShiftService;
import com.lulak.frugo.service.referenceData.CRUD.ShiftCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/shifts")
public class AdminShiftController {

    private final AdminShiftService adminShiftService;
    private final ShiftCrudService shiftCrudService;

    public AdminShiftController(
            AdminShiftService adminShiftService,
            ShiftCrudService shiftCrudService){
        this.adminShiftService = adminShiftService;
        this.shiftCrudService = shiftCrudService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('REFERENCE_DATA_READ')")
    public List<AdminShiftDto> getShifts(){ return adminShiftService.getAllShifts(); }

    @PostMapping
    @PreAuthorize("hasAuthority('REFERENCE_DATA_CREATE')")
    public ResponseEntity<Shift> createShift(
            @Valid @RequestBody ShiftCreateDto dto
    ){
        Shift shift = shiftCrudService.createShift(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(shift);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_DATA_EDIT')")
    public ResponseEntity<Shift> updateShift(
            @PathVariable Integer id,
            @Valid @RequestBody ShiftCreateDto dto
    ){
        Shift shift = shiftCrudService.updateShift(id, dto);

        return ResponseEntity.ok(shift);
    }
}
