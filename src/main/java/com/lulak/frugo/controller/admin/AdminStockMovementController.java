package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.event.AdminStockMovementDetailDto;
import com.lulak.frugo.dto.event.AdminStockMovementListDto;
import com.lulak.frugo.service.event.AdminStockMovementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/stockMovements")
@CrossOrigin("*")
public class AdminStockMovementController {

    private final AdminStockMovementService adminStockMovementService;

    public AdminStockMovementController(
            AdminStockMovementService adminStockMovementService
    ){
        this.adminStockMovementService = adminStockMovementService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminStockMovementListDto> getStockMovements(
            @RequestParam(required = false) String eventCode,
            @RequestParam(required = false) String etiNumber,
            @RequestParam(required = false) String fromLocation,
            @RequestParam(required = false) String toLocation,
            @RequestParam(required = false) String employeeNumber
    ){
        return adminStockMovementService.getFilteredStockMovements(
                eventCode,
                etiNumber,
                fromLocation,
                toLocation,
                employeeNumber
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminStockMovementDetailDto getStockMovementDetail(
            @PathVariable Integer id
    ){
        return adminStockMovementService.getStockMovementDetail(id);
    }
}
