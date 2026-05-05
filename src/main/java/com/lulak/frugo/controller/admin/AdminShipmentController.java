package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.AdminShipmentDto;
import com.lulak.frugo.service.AdminShipmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/shipments")
@CrossOrigin("*")
public class AdminShipmentController {

    private final AdminShipmentService adminShipmentService;

    public AdminShipmentController(AdminShipmentService adminShipmentService){
        this.adminShipmentService = adminShipmentService;
    }

    @GetMapping
    public List<AdminShipmentDto> getShipments(
            @RequestParam(required = false) String shipmentNumber,
            @RequestParam(required = false) String orderNumber,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String customerName
    ){
        return adminShipmentService.getFilteredShipments(shipmentNumber, orderNumber, status, customerName);
    }
}
