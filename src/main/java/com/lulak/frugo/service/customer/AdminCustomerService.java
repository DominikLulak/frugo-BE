package com.lulak.frugo.service.customer;

import com.lulak.frugo.dto.customer.AdminCustomerDetailDto;
import com.lulak.frugo.dto.customer.AdminCustomerListDto;
import com.lulak.frugo.model.customer.Customer;
import com.lulak.frugo.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCustomerService {

    private final CustomerRepository customerRepository;

    public AdminCustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<AdminCustomerListDto> getFilteredCustomers(
            String name,
            String companyId,
            String city,
            String postalCode
    ){
        return customerRepository.getFilteredCustomers(
                name,
                companyId,
                city,
                postalCode
        );
    }

    public AdminCustomerDetailDto getCustomerDetail(Integer id){
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found!"));

        return new AdminCustomerDetailDto(
                c.getId(),
                c.getName(),
                c.getCompanyId(),
                c.getStreet(),
                c.getHouseNumber(),
                c.getCity(),
                c.getPostalCode(),
                c.getCountry().getCode(),
                c.getCountry().getName(),
                c.isRegistered()
        );
    }
}
