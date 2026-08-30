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
            p.name,
            p.productType.name,
            p.productType.category.name,
            p.pricePerUnit,
            p.shelfLifeDays,
            p.forSale
        )
        FROM Product p
        WHERE 
            (:category IS NULL OR 
                LOWER(p.productType.category.name)
                LIKE LOWER(CONCAT('%', :category, '%')))
        
        AND (:name IS NULL OR 
            LOWER(p.name)
            LIKE LOWER(CONCAT('%', :name, '%')))
        
        AND (:productType IS NULL OR 
            LOWER(p.productType.name)
            LIKE LOWER(CONCAT('%', :productType, '%')))
        
        AND (:productCode IS NULL OR 
            LOWER(p.productCode)
            LIKE LOWER(CONCAT('%', :productCode, '%')))
    """)
    List<AdminProductListDto> getFilteredProducts(
            @Param("category") String category,
            @Param("name") String name,
            @Param("productType") String productType,
            @Param("productCode") String productCode
    );
}
