package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.warehouse.AdminWarehouseSectorTypeDto;
import com.lulak.frugo.dto.warehouse.CRUD.SectorTypeCreateDto;
import com.lulak.frugo.model.warehouse.SectorType;
import com.lulak.frugo.service.warehouse.AdminSectorTypeService;
import com.lulak.frugo.service.warehouse.CRUD.SectorTypeCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/warehouses/sectorTypes")
public class AdminSectorTypeController {

    private final AdminSectorTypeService adminSectorTypeService;
    private final SectorTypeCrudService sectorTypeCrudService;

    public AdminSectorTypeController(
            AdminSectorTypeService adminSectorTypeService,
            SectorTypeCrudService sectorTypeCrudService){
        this.adminSectorTypeService = adminSectorTypeService;
        this.sectorTypeCrudService = sectorTypeCrudService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminWarehouseSectorTypeDto> getSectorTypes(){ return adminSectorTypeService.getAllSectorTypes(); }

    @PostMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_CREATE')")
    public ResponseEntity<SectorType> createSectorType(
            @Valid @RequestBody SectorTypeCreateDto dto
    ){
        SectorType sectorType = sectorTypeCrudService.createSectorType(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(sectorType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_EDIT')")
    public ResponseEntity<SectorType> updateSectorType(
            @PathVariable Integer id,
            @Valid @RequestBody SectorTypeCreateDto dto
    ){
        SectorType sectorType = sectorTypeCrudService.updateSectorType(id, dto);

        return ResponseEntity.ok(sectorType);
    }
}
