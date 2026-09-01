package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.pallet.AdminPalletWarehouseItemDto;
import com.lulak.frugo.service.pallet.AdminPalletService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/pallets")
@CrossOrigin("*")
public class AdminPalletController {

    private final AdminPalletService adminPalletService;

    public AdminPalletController(
            AdminPalletService adminPalletService
    ){
        this.adminPalletService = adminPalletService;
    }

    @GetMapping("/{id}/items")
    @PreAuthorize("hasAuthority('SHIPMENT_READ')")
    public List<AdminPalletWarehouseItemDto> getPalletItems(
            @PathVariable Integer id
    ){
        return adminPalletService.getPalletItems(id);
    }
}
