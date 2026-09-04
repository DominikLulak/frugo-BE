package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.customer.AdminCustomerDetailDto;
import com.lulak.frugo.dto.customer.AdminCustomerListDto;
import com.lulak.frugo.service.customer.AdminCustomerService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    public List<AdminCustomerListDto> getCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String companyId,
            @RequestParam(required = false) String countryCode,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String postalCode,
            @RequestParam(required = false) Boolean registered
    ){
        return adminCustomerService.getFilteredCustomers(
                name,
                companyId,
                countryCode,
                city,
                postalCode,
                registered
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    public AdminCustomerDetailDto getCustomerDetail(
            @PathVariable Integer id
    ){
        return adminCustomerService.getCustomerDetail(id);
    }
}
