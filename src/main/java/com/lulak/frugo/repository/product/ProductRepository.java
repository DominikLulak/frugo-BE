package com.lulak.frugo.repository.product;

import com.lulak.frugo.dto.product.AdminProductListDto;
import com.lulak.frugo.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByProductType_Category_Code(String categoryCode);

    @Query("""
        SELECT new com.lulak.frugo.dto.product.AdminProductListDto(
            p.id,
            p.productCode,
            p.productType.category.code,
            p.productType.name,
            p.name,
            p.pricePerUnit,
            p.shelfLifeDays,
            p.forSale,
            COALESCE(
                SUM(w.quantity - w.allocatedQuantity),
                0
            ),
            p.reorderPoint,
            p.minimumStock 
        )
        FROM Product p
        LEFT JOIN WarehouseItem w
            ON w.product = p
        WHERE
            (
                COALESCE(:category, '') = ''
                OR p.productType.category.code
                    LIKE CONCAT('%', :category, '%')   
            )
        
        AND (
            COALESCE(:productType, '') = ''
            OR p.productType.name
                LIKE CONCAT('%', :productType, '%')    
        )
        
        AND (
            COALESCE(:productCode, '') = ''
            OR p.productCode
                LIKE CONCAT('%', :productCode, '%')    
        )
            
        GROUP BY 
            p.id,
            p.productCode,
            p.productType.category.code,
            p.productType.name,
            p.name,
            p.pricePerUnit,
            p.shelfLifeDays,
            p.forSale,
            p.reorderPoint,
            p.minimumStock
    """)
    List<AdminProductListDto> getFilteredProducts(
            @Param("category") String category,
            @Param("productType") String productType,
            @Param("productCode") String productCode
    );
}
