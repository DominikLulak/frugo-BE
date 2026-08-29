package com.lulak.frugo.repository.customer;

import com.lulak.frugo.dto.customer.AdminCustomerListDto;
import com.lulak.frugo.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.customer.AdminCustomerListDto(
            c.id,
            c.name,
            c.companyId,
            c.city,
            c.postalCode,
            c.registered
        )
        FROM Customer c
        WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:companyId IS NULL OR c.companyId LIKE CONCAT('%', :companyId, '%'))
        AND (:city IS NULL OR LOWER(c.city) LIKE LOWER(CONCAT('%', :city, '%')))
        AND (:postalCode IS NULL OR c.postalCode LIKE CONCAT('%', :postalCode, '%'))
    """)
    List<AdminCustomerListDto> getFilteredCustomers(
            String name,
            String companyId,
            String city,
            String postalCode
    );
}
