package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.purchaseOrders.AdminSupplierListDto;
import com.lulak.frugo.service.purchaseOrders.AdminSupplierService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(required = false) String internalCode
    ){
        return adminSupplierService.getFilteredSuppliers(
                name,
                internalCode
        );
    }
}
