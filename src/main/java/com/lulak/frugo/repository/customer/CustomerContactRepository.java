package com.lulak.frugo.repository.customer;

import com.lulak.frugo.model.customer.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerContactRepository extends JpaRepository<CustomerContact, Integer> {

    List<CustomerContact> findByCustomerId(Integer customerId);
}
