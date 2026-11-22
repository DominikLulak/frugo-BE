package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.pallet.AdminPalletDetailDto;
import com.lulak.frugo.dto.pallet.AdminPalletListDto;
import com.lulak.frugo.dto.pallet.AdminPalletWarehouseItemDto;
import com.lulak.frugo.dto.pallet.transaction.MovePalletDto;
import com.lulak.frugo.service.pallet.AdminPalletService;
import com.lulak.frugo.service.pallet.transactions.PalletMovementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/pallets")
@CrossOrigin("*")
public class AdminPalletController {

    private final AdminPalletService adminPalletService;
    private final PalletMovementService palletMovementService;

    public AdminPalletController(
            AdminPalletService adminPalletService,
            PalletMovementService palletMovementService){
        this.adminPalletService = adminPalletService;
        this.palletMovementService = palletMovementService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('STOCKS_READ')")
    public List<AdminPalletListDto> getFilteredPallets(
            @RequestParam(required = false) String etiNumber,
            @RequestParam(required = false) String locationCode,
            @RequestParam(required = false) Boolean isClosed
    ){
        return adminPalletService.getFilteredPallets(
                etiNumber,
                locationCode,
                isClosed
        );
    }

    @GetMapping("/{id}/items")
    @PreAuthorize("hasAuthority('STOCKS_READ')")
    public AdminPalletDetailDto getPalletDetail(
            @PathVariable Integer id
    ){
        return adminPalletService.getPalletDetail(id);
    }

    @PostMapping("/move")
    @PreAuthorize("hasAuthority('STOCKS_MOVE')")
    public void movePallet(
            @RequestBody MovePalletDto dto
    ){
        palletMovementService.movePallet(dto);
    }
}
