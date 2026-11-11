package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.referenceData.AdminCountryDto;
import com.lulak.frugo.dto.referenceData.CRUD.CountryCreateDto;
import com.lulak.frugo.model.Country;
import com.lulak.frugo.service.referenceData.AdminCountryService;
import com.lulak.frugo.service.referenceData.CRUD.CountryCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/countries")
public class AdminCountryController {

    private final AdminCountryService adminCountryService;
    private final CountryCrudService countryCrudService;

    public AdminCountryController(
            AdminCountryService adminCountryService,
            CountryCrudService countryCrudService){
        this.adminCountryService = adminCountryService;
        this.countryCrudService = countryCrudService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('REFERENCE_DATA_READ')")
    public List<AdminCountryDto> getCountries(){ return adminCountryService.getAllCountries(); }

    @PostMapping
    @PreAuthorize("hasAuthority('REFERENCE_DATA_CREATE')")
    public ResponseEntity<Country> createCountry(
            @Valid @RequestBody CountryCreateDto dto
    ){
        Country country = countryCrudService.createCountry(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(country);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_DATA_EDIT')")
    public ResponseEntity<Country> updateCountry(
            @PathVariable Integer id,
            @Valid @RequestBody CountryCreateDto dto
    ) {
        Country country = countryCrudService.updateCountry(id, dto);

        return ResponseEntity.ok(country);
    }
}
