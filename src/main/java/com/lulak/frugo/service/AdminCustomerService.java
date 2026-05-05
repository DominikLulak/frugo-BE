package com.lulak.frugo.service;

import com.lulak.frugo.dto.AdminCustomerDetailDto;
import com.lulak.frugo.dto.AdminCustomerListDto;
import com.lulak.frugo.model.Customer;
import com.lulak.frugo.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCustomerService {

    private final CustomerRepository customerRepository;

    public AdminCustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<AdminCustomerListDto> getFilteredCustomers(
            String customerNumber,
            String name,
            String email,
            String phoneNumber
    ){
        return customerRepository.getFilteredCustomers(
                customerNumber,
                name,
                email,
                phoneNumber
        );
    }

    public AdminCustomerDetailDto getCustomerDetail(String customerNumber){
        Customer c = customerRepository.findByCustomerNumber(customerNumber)
                .orElseThrow(() -> new RuntimeException("Customer not found!"));

        return new AdminCustomerDetailDto(
                c.getCustomerNumber(),
                c.getName(),
                c.getCountry(),
                c.getAddress(),
                c.getExecutive(),
                c.getEmail(),
                c.getPhoneNumber()
        );
    }
}
