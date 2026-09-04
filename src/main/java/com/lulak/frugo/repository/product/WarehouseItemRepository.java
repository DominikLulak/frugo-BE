package com.lulak.frugo.repository.product;

import com.lulak.frugo.dto.product.AdminWarehouseItemDetailDto;
import com.lulak.frugo.dto.product.AdminWarehouseItemListDto;
import com.lulak.frugo.model.product.WarehouseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseItemRepository extends JpaRepository<WarehouseItem, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.product.AdminWarehouseItemListDto(
            w.id,
            c.code,
            pt.name,
            p.name,
            p.productCode,
            pallet.palletNumber,
            w.etiNumber,
            w.quantity,
            w.allocatedQuantity,
            (w.quantity - w.allocatedQuantity),
            w.expirationDate,
            wh.code,
            l.code
        )
        FROM WarehouseItem w
        JOIN w.product p
        JOIN p.productType pt
        JOIN pt.category c
        JOIN w.location l
        JOIN l.sector s
        JOIN s.warehouse wh
        LEFT JOIN PalletWarehouseItem pwi
            ON pwi.warehouseItem.id = w.id
        LEFT JOIN pwi.pallet pallet
        
        WHERE (
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
    List<AdminWarehouseItemListDto> getFilteredWarehouseItems(
            @Param("productCode") String productCode,
            @Param("name") String name,
            @Param("category") String category,
            @Param("productType") String productType,
            @Param("etiNumber") String etiNumber,
            @Param("warehouseCode") String warehouseCode
    );

    @Query("""
        SELECT new com.lulak.frugo.dto.product.AdminWarehouseItemDetailDto(
            w.id,
            c.code,
            pt.name,
            p.name,
            p.productCode,
            pallet.palletNumber,
            w.etiNumber,
            packaging.code,
            w.quantityPerPackage,
            subpackaging.code,
            w.quantityPerSubpackage,
            w.quantity,
            w.allocatedQuantity,
            (w.quantity - w.allocatedQuantity),
            country.code,
            w.receivedAt,
            w.expirationDate,
            wh.code,
            l.code
        )
        FROM WarehouseItem w
        JOIN w.product p
        JOIN p.productType pt
        JOIN pt.category c
        JOIN w.packagingType packaging
        LEFT JOIN w.subpackageType subpackaging
        JOIN w.countryOfOrigin country
        JOIN w.location l
        JOIN l.sector s
        JOIN s.warehouse wh
        LEFT JOIN PalletWarehouseItem pwi
            ON pwi.warehouseItem.id = w.id
        LEFT JOIN pwi.pallet pallet
        
        WHERE w.id = :id
    """)
    AdminWarehouseItemDetailDto getWarehouseItemDetail(
            @Param("id") Integer id
    );
}
