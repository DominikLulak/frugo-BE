package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.event.AdminStockMovementDetailDto;
import com.lulak.frugo.dto.event.AdminStockMovementListDto;
import com.lulak.frugo.service.event.StockMovementQueryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/stockMovements")
@CrossOrigin("*")
public class AdminStockMovementController {

    private final StockMovementQueryService stockMovementQueryService;

    public AdminStockMovementController(
            StockMovementQueryService stockMovementQueryService
    ){
        this.stockMovementQueryService = stockMovementQueryService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EVENTS_READ')")
    public List<AdminStockMovementListDto> getStockMovements(
            @RequestParam(required = false) String eventCode,
            @RequestParam(required = false) String etiNumber,
            @RequestParam(required = false) String fromLocation,
            @RequestParam(required = false) String toLocation,
            @RequestParam(required = false) String employeeNumber
    ){
        return stockMovementQueryService.getFilteredStockMovements(
                eventCode,
                etiNumber,
                fromLocation,
                toLocation,
                employeeNumber
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENTS_READ')")
    public AdminStockMovementDetailDto getStockMovementDetail(
            @PathVariable Long id
    ){
        return stockMovementQueryService.getStockMovementDetail(id);
    }
}
