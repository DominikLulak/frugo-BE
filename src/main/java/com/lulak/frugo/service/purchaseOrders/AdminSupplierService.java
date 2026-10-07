package com.lulak.frugo.service.purchaseOrders;

import com.lulak.frugo.dto.purchaseOrders.AdminSupplierListDto;
import com.lulak.frugo.repository.purchaseOrders.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSupplierService {

    private final SupplierRepository supplierRepository;

    public AdminSupplierService(
            SupplierRepository supplierRepository
    ){
        this.supplierRepository = supplierRepository;
    }

    public List<AdminSupplierListDto> getFilteredSuppliers(
            String name,
            String internalCode
    ){
        return supplierRepository.getFilteredSuppliers(
                name,
                internalCode
        );
    }
}
