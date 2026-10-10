package com.lulak.frugo.repository.customer;

import com.lulak.frugo.model.customer.Customer;
import com.lulak.frugo.model.customer.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerContactRepository extends JpaRepository<CustomerContact, Integer> {

    List<CustomerContact> findByCustomerId(Integer customerId);

    CustomerContact findCustomerContactById(
            @Param("id") Integer id
    );
}
