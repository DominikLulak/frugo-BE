package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.customer.AdminCustomerDetailDto;
import com.lulak.frugo.dto.customer.AdminCustomerListDto;
import com.lulak.frugo.dto.customer.CRUD.CustomerContactCrudDto;
import com.lulak.frugo.dto.customer.CRUD.CustomerCreateDto;
import com.lulak.frugo.model.customer.Customer;
import com.lulak.frugo.service.customer.AdminCustomerService;
import com.lulak.frugo.service.customer.CRUD.CustomerCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/customers")
@CrossOrigin("*")
public class AdminCustomerController {

    private final AdminCustomerService adminCustomerService;
    private final CustomerCrudService customerCrudService;

    public AdminCustomerController(
            AdminCustomerService adminCustomerService,
            CustomerCrudService customerCrudService
    ){
        this.adminCustomerService = adminCustomerService;
        this.customerCrudService = customerCrudService;
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

    @PostMapping
    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    public ResponseEntity<Customer> createCustomer(
            @Valid @RequestBody CustomerCreateDto dto
    ){
        Customer customer = customerCrudService.createCustomer(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Integer id,
            @Valid @RequestBody CustomerCreateDto dto
    ){
        Customer customer = customerCrudService.updateCustomer(id, dto);

        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Integer id
    ){
        customerCrudService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{customerId}/customerContacts")
    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    public ResponseEntity<Void> addCustomerContact(
            @PathVariable Integer customerId,
            @Valid @RequestBody CustomerContactCrudDto dto
    ){
        customerCrudService.addCustomerContact(
                customerId,
                dto
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{customerId}/customerContacts/{customerContactId}")
    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    public ResponseEntity<Void> updateCustomerContact(
            @PathVariable Integer customerId,
            @PathVariable Integer customerContactId,
            @Valid @RequestBody CustomerContactCrudDto dto
    ){
        customerCrudService.updateCustomerContact(
                customerId,
                customerContactId,
                dto
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}/customerContacts/{customerContactId}")
    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    public ResponseEntity<Void> deleteCustomerContact(
            @PathVariable Integer customerId,
            @PathVariable Integer customerContactId
    ){
        customerCrudService.deleteCustomerContact(
                customerId,
                customerContactId
        );

        return ResponseEntity.noContent().build();
    }
}
