package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.purchaseOrders.AdminSupplierListDto;
import com.lulak.frugo.dto.purchaseOrders.SupplierCreateDto;
import com.lulak.frugo.dto.purchaseOrders.SupplierUpdateDto;
import com.lulak.frugo.model.purchaseOrders.Supplier;
import com.lulak.frugo.service.purchaseOrders.AdminSupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/suppliers")
@CrossOrigin("*")
public class AdminSupplierController {

    private final AdminSupplierService adminSupplierService;

    public AdminSupplierController(AdminSupplierService adminSupplierService){
        this.adminSupplierService = adminSupplierService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public List<AdminSupplierListDto> getSuppliers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String internalCode,
            @RequestParam(required = false) Boolean isActive
    ){
        return adminSupplierService.getFilteredSuppliers(
                name,
                internalCode,
                isActive
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<Supplier> createSupplier(
            @Valid @RequestBody SupplierCreateDto dto
    ){
        Supplier supplier = adminSupplierService.createSupplier(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(supplier);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<Supplier> updateSupplier(
            @PathVariable Integer id,
            @Valid @RequestBody SupplierUpdateDto dto
    ){
        Supplier supplier = adminSupplierService.updateSupplier(id, dto);

        return ResponseEntity.ok(supplier);
    }

    @PatchMapping("/{id}/active")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<Supplier> setSupplierActive(
            @PathVariable Integer id,
            @RequestParam boolean active
    ){
        Supplier supplier = adminSupplierService.setSupplierActive(id, active);

        return ResponseEntity.ok(supplier);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<Void> deleteSupplier(
            @PathVariable Integer id
    ){
        adminSupplierService.deleteSupplier(id);

        return ResponseEntity.noContent().build();
    }
}
