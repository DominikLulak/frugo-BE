package com.lulak.frugo.service.customer;

import com.lulak.frugo.dto.customer.AdminCustomerContactDto;
import com.lulak.frugo.dto.customer.AdminCustomerDetailDto;
import com.lulak.frugo.dto.customer.AdminCustomerListDto;
import com.lulak.frugo.model.customer.Customer;
import com.lulak.frugo.repository.customer.CustomerContactRepository;
import com.lulak.frugo.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerContactRepository customerContactRepository;

    public AdminCustomerService(
            CustomerRepository customerRepository,
            CustomerContactRepository customerContactRepository
    ){
        this.customerRepository = customerRepository;
        this.customerContactRepository = customerContactRepository;
    }

    public List<AdminCustomerListDto> getFilteredCustomers(
            String name,
            String companyId,
            String countryCode,
            String city,
            String postalCode,
            Boolean registered
    ){
        return customerRepository.getFilteredCustomers(
                name,
                companyId,
                countryCode,
                city,
                postalCode,
                registered
        );
    }

    public AdminCustomerDetailDto getCustomerDetail(Integer id){

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found: " + id)
                );
        List<AdminCustomerContactDto> contacts =
                customerContactRepository.findByCustomerId(id)
                        .stream()
                        .map(contact -> new AdminCustomerContactDto(
                                contact.getId(),
                                contact.getName(),
                                contact.getPhoneNumber(),
                                contact.getEmail(),
                                contact.isPrimary()
                        ))
                        .toList();

        return new AdminCustomerDetailDto(
                customer.getId(),
                customer.getName(),
                customer.getCompanyId(),
                customer.getCountry().getCode(),
                customer.getCity(),
                customer.getPostalCode(),
                customer.getStreet(),
                customer.getHouseNumber(),
                customer.isRegistered(),
                contacts
        );
    }
}
