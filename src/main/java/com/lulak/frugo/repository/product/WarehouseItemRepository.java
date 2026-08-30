package com.lulak.frugo.repository.product;

import com.lulak.frugo.model.product.WarehouseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseItemRepository extends JpaRepository<WarehouseItem, Integer> {

    @Query("""
        SELECT w
        FROM WarehouseItem w
        JOIN w.product p
        JOIN p.productType pt
        JOIN pt.category c
        JOIN w.location l
        JOIN l.sector s
        JOIN s.warehouse wh
        WHERE 
            (
                COALESCE(:productCode, '') = ''
                OR p.productCode LIKE CONCAT('%', :productCode, '%') 
            )
        
        AND (
                COALESCE(:name, '') = ''
                OR p.name LIKE CONCAT('%', :name, '%') 
            )
        
        AND (
                COALESCE(:category, '') = ''
                OR c.code LIKE CONCAT('%', :category, '%') 
            )
            
        AND (
                COALESCE(:productType, '') = ''
                OR pt.name LIKE CONCAT('%', :productType, '%') 
            )
        
        AND (
                COALESCE(:etiNumber, '') = ''
                OR w.etiNumber LIKE CONCAT('%', :etiNumber, '%') 
            )
        
        AND (
                COALESCE(:warehouseCode, '') = ''
                OR wh.code LIKE CONCAT('%', :warehouseCode, '%') 
            )
    """)
    List<WarehouseItem> getFilteredWarehouseItems(
            @Param("productCode") String productCode,
            @Param("name") String name,
            @Param("category") String category,
            @Param("productType") String productType,
            @Param("etiNumber") String etiNumber,
            @Param("warehouseCode") String warehouseCode
    );
}
