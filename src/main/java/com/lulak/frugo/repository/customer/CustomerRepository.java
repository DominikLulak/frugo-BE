package com.lulak.frugo.repository.customer;

import com.lulak.frugo.dto.customer.AdminCustomerListDto;
import com.lulak.frugo.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.customer.AdminCustomerListDto(
            cu.id,
            cu.name,
            cu.companyId,
            co.code,
            cu.city,
            cu.postalCode,
            cu.registered
        )
        FROM Customer cu
        JOIN cu.country co
        WHERE (
            COALESCE(:name, '') = ''
            OR LOWER(cu.name)
                LIKE LOWER(CONCAT('%', :name, '%'))\s
        )
        AND(
            COALESCE(:companyId, '') = ''
            OR LOWER(cu.companyId)
                LIKE LOWER(CONCAT('%', :companyId, '%'))\s
        )
        AND(
            COALESCE(:countryCode, '') = ''
            OR LOWER(co.code)
                LIKE LOWER(CONCAT('%', :countryCode, '%'))\s
        )
        AND(
            COALESCE(:city, '') = ''
            OR LOWER(cu.city)
                LIKE LOWER(CONCAT('%', :city, '%'))\s
        )
        AND(
            COALESCE(:postalCode, '') = ''
            OR LOWER(cu.postalCode)
                LIKE LOWER(CONCAT('%', :postalCode, '%'))\s
        )\s
        AND(
            :registered IS NULL
            OR cu.registered = :registered
        )
   \s""")
    List<AdminCustomerListDto> getFilteredCustomers(
            @Param("name") String name,
            @Param("companyId") String companyId,
            @Param("countryCode") String countryCode,
            @Param("city") String city,
            @Param("postalCode") String postalCode,
            @Param("registered") Boolean registered
    );
}
