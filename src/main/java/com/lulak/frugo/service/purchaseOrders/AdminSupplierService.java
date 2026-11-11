package com.lulak.frugo.service.purchaseOrders;

import com.lulak.frugo.dto.purchaseOrders.AdminSupplierListDto;
import com.lulak.frugo.dto.purchaseOrders.SupplierCreateDto;
import com.lulak.frugo.dto.purchaseOrders.SupplierUpdateDto;
import com.lulak.frugo.model.purchaseOrders.Supplier;
import com.lulak.frugo.repository.purchaseOrders.PurchaseOrderRepository;
import com.lulak.frugo.repository.purchaseOrders.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSupplierService {

    private final SupplierRepository supplierRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public AdminSupplierService(
            SupplierRepository supplierRepository,
            PurchaseOrderRepository purchaseOrderRepository
    ){
        this.supplierRepository = supplierRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public List<AdminSupplierListDto> getFilteredSuppliers(
            String name,
            String internalCode,
            Boolean isActive
    ){
        return supplierRepository.getFilteredSuppliers(
                name,
                internalCode,
                isActive
        );
    }

    //CREATE
    public Supplier createSupplier(SupplierCreateDto dto){
        Supplier supplier = new Supplier();

        supplier.setName(dto.getName());
        supplier.setInternalCode(dto.getInternalCode());
        supplier.setActive(true);

        return supplierRepository.save(supplier);
    }

    //UPDATE
    public Supplier updateSupplier(
            Integer id,
            SupplierUpdateDto dto
    ){
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Supplier not found!")
                );

        supplier.setName(dto.getName());
        supplier.setInternalCode(dto.getInternalCode());

        return supplierRepository.save(supplier);
    }

    //AKTIVACE / DEAKTIVACE
    public Supplier setSupplierActive(
            Integer id,
            boolean active
    ){
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Supplier not found!")
                );

        supplier.setActive(active);

        return supplierRepository.save(supplier);
    }

    //DELETE
    public void deleteSupplier(Integer id){
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Supplier not found!")
                );

        boolean isUsed = purchaseOrderRepository.existsBySupplierId(id);

        if(isUsed){
            throw new RuntimeException(
                    "Supplier cannot be deleted because it is used by a purchase order!"
            );
        }

        supplierRepository.delete(supplier);
    }
}
