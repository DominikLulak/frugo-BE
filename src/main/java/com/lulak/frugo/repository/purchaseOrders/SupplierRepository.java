package com.lulak.frugo.repository.purchaseOrders;

import com.lulak.frugo.dto.purchaseOrders.AdminSupplierListDto;
import com.lulak.frugo.model.purchaseOrders.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.purchaseOrders.AdminSupplierListDto(
            sup.id,
            sup.name,
            sup.internalCode
        )
        FROM Supplier sup
        WHERE (
            COALESCE(:name, '') = ''
            OR sup.name LIKE CONCAT('%', :name, '%') 
        )
        AND (
            COALESCE(:internalCode, '') = ''
            OR sup.internalCode LIKE CONCAT('%', :internalCode, '%') 
        )
    """)
    List<AdminSupplierListDto> getFilteredSuppliers(
            @Param("name") String name,
            @Param("internalCode") String internalCode
    );
}
