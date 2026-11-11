package com.lulak.frugo.service.customer.CRUD;

import com.lulak.frugo.dto.customer.CRUD.CustomerContactCrudDto;
import com.lulak.frugo.dto.customer.CRUD.CustomerCreateDto;
import com.lulak.frugo.model.Country;
import com.lulak.frugo.model.customer.Customer;
import com.lulak.frugo.model.customer.CustomerContact;
import com.lulak.frugo.repository.customer.CustomerContactRepository;
import com.lulak.frugo.repository.customer.CustomerRepository;
import com.lulak.frugo.repository.order.OrderRepository;
import com.lulak.frugo.repository.referenceData.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CustomerCrudService {

    private final CountryRepository countryRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final CustomerContactRepository customerContactRepository;

    public CustomerCrudService(
            CountryRepository countryRepository,
            CustomerRepository customerRepository,
            OrderRepository orderRepository,
            CustomerContactRepository customerContactRepository
    ){
        this.countryRepository = countryRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.customerContactRepository = customerContactRepository;
    }

    @Transactional
    public Customer createCustomer(
            CustomerCreateDto dto
    ){
        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found: " + dto.getCountryId()));

        Customer customer = new Customer();

        customer.setName(dto.getName());
        customer.setCompanyId(dto.getCompanyId());
        customer.setCountry(country);
        customer.setCity(dto.getCity());
        customer.setPostalCode(dto.getPostalCode());
        customer.setStreet(dto.getStreet());
        customer.setHouseNumber(dto.getHouseNumber());
        customer.setRegistered(dto.isRegistered());

        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(
            Integer customerId,
            CustomerCreateDto dto
    ){
        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found: " + dto.getCountryId()));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found " + customerId));

        customer.setName(dto.getName());
        customer.setCompanyId(dto.getCompanyId());
        customer.setCountry(country);
        customer.setCity(dto.getCity());
        customer.setPostalCode(dto.getPostalCode());
        customer.setStreet(dto.getStreet());
        customer.setHouseNumber(dto.getHouseNumber());
        customer.setRegistered(dto.isRegistered());

        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Integer customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found!"));

        boolean isUsed = orderRepository.existsByCustomerId(customerId);

        if(isUsed){
            throw new RuntimeException("Customer cannot be deleted because it is used by a order!");
        }

        customerRepository.delete(customer);
    }

    @Transactional
    public void addCustomerContact(
            Integer customerId,
            CustomerContactCrudDto dto
    ){
        Customer customer = customerRepository
                .findCustomerById(customerId);

        if(customer == null){
            throw new RuntimeException("Customer not found " + customerId);
        }

        CustomerContact customerContact = new CustomerContact();

        customerContact.setCustomer(customer);
        customerContact.setName(dto.getName());
        customerContact.setPhoneNumber(dto.getPhoneNumber());
        customerContact.setEmail(dto.getEmail());
        customerContact.setPrimary(dto.isPrimary());

        customerContactRepository.save(customerContact);
    }

    @Transactional
    public void updateCustomerContact(
            Integer customerId,
            Integer customerContactId,
            CustomerContactCrudDto dto
    ){
        Customer customer = customerRepository
                .findCustomerById(customerId);

        if(customer == null){
            throw new RuntimeException("Customer not found " + customerId);
        }

        CustomerContact customerContact = customerContactRepository
                .findById(customerContactId)
                .orElseThrow(() -> new RuntimeException("Customer contact not found " + customerContactId));

        customerContact.setCustomer(customer);
        customerContact.setName(dto.getName());
        customerContact.setPhoneNumber(dto.getPhoneNumber());
        customerContact.setEmail(dto.getEmail());
        customerContact.setPrimary(dto.isPrimary());

        customerContactRepository.save(customerContact);
    }

    @Transactional
    public void deleteCustomerContact(
            Integer customerId,
            Integer customerContactId
    ){
        Customer customer = customerRepository
                .findCustomerById(customerId);

        if(customer == null){
            throw new RuntimeException("Customer not found " + customerId);
        }

        CustomerContact customerContact = customerContactRepository
                .findCustomerContactById(customerContactId);

        if(customerContact == null){
            throw new RuntimeException("Customer contact not found " + customerContactId);
        }

        customerContactRepository.delete(customerContact);
    }
}
