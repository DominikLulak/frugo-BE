package com.lulak.frugo.repository;

import com.lulak.frugo.dto.AdminCustomerListDto;
import com.lulak.frugo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
        SELECT new com.lulak.frugo.dto.AdminCustomerListDto(
            c.customerNumber,
            c.name,
            c.email,
            c.phoneNumber
        )
        FROM Customer c
        WHERE (:customerNumber IS NULL OR c.customerNumber LIKE %:customerNumber%)
        AND (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%')))
        AND (:phoneNumber IS NULL OR c.phoneNumber LIKE %:phoneNumber%)
    """)
    List<AdminCustomerListDto> getFilteredCustomers(
            String customerNumber,
            String name,
            String email,
            String phoneNumber
    );

    Optional<Customer> findByCustomerNumber(String customerNumber);
}
