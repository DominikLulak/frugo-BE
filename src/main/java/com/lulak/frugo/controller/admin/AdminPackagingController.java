package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.product.AdminPackagingDto;
import com.lulak.frugo.dto.referenceData.CRUD.PackagingCreateDto;
import com.lulak.frugo.model.product.Packaging;
import com.lulak.frugo.service.product.AdminPackagingService;
import com.lulak.frugo.service.referenceData.CRUD.PackagingCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/warehouses/packaging")
public class AdminPackagingController {

    private final AdminPackagingService adminPackagingService;
    private final PackagingCrudService packagingCrudService;

    public AdminPackagingController(
            AdminPackagingService adminPackagingService,
            PackagingCrudService packagingCrudService){
        this.adminPackagingService = adminPackagingService;
        this.packagingCrudService = packagingCrudService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('REFERENCE_DATA_READ')")
    public List<AdminPackagingDto> getPackaging(){ return adminPackagingService.getAllPackaging(); }

    @PostMapping
    @PreAuthorize("hasAuthority('REFERENCE_DATA_CREATE')")
    public ResponseEntity<Packaging> createPackaging(
            @Valid @RequestBody PackagingCreateDto dto
    ){
        Packaging packaging = packagingCrudService.createPackaging(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(packaging);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_DATA_EDIT')")
    public ResponseEntity<Packaging> updatePackaging(
            @PathVariable Integer id,
            @Valid @RequestBody PackagingCreateDto dto
    ){
        Packaging packaging = packagingCrudService.updatePackaging(id, dto);

        return ResponseEntity.ok(packaging);
    }

}
