package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.shipment.AdminShipmentDetailDto;
import com.lulak.frugo.dto.shipment.AdminShipmentListDto;
import com.lulak.frugo.service.shipment.AdminShipmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/shipments")
@CrossOrigin("*")
public class AdminShipmentController {

    private final AdminShipmentService adminShipmentService;

    public AdminShipmentController(
            AdminShipmentService adminShipmentService
    ){
        this.adminShipmentService = adminShipmentService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SHIPMENT_READ')")
    public List<AdminShipmentListDto> getShipments(
            @RequestParam(required = false) String shipmentNumber,
            @RequestParam(required = false) String orderNumber,
            @RequestParam(required = false) String statusCode
    ){
        return adminShipmentService.getFilteredShipments(
                shipmentNumber,
                orderNumber,
                statusCode
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SHIPMENT_READ')")
    public AdminShipmentDetailDto getShipmentDetail(
            @PathVariable Integer id
    ){
        return adminShipmentService.getShipmentDetail(id);
    }
}
