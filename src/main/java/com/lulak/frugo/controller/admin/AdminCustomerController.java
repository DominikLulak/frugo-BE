package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.AdminCustomerDetailDto;
import com.lulak.frugo.dto.AdminCustomerListDto;
import com.lulak.frugo.service.AdminCustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/customers")
@CrossOrigin("*")
public class AdminCustomerController {

    private final AdminCustomerService adminCustomerService;

    public AdminCustomerController(AdminCustomerService adminCustomerService){
        this.adminCustomerService = adminCustomerService;
    }

    @GetMapping
    public List<AdminCustomerListDto> getCustomers(
            @RequestParam(required = false) String customerNumber,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber
    ){
        return adminCustomerService.getFilteredCustomers(
                customerNumber,
                name,
                email,
                phoneNumber
        );
    }

    @GetMapping("/{customerNumber}")
    public AdminCustomerDetailDto getCustomerDetail(
            @PathVariable String customerNumber
    ){
        return adminCustomerService.getCustomerDetail(customerNumber);
    }
}
