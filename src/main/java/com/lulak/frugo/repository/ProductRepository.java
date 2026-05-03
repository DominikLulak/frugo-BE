package com.lulak.frugo.repository;

import com.lulak.frugo.controller.admin.AdminProductController;
import com.lulak.frugo.model.Product;
import com.lulak.frugo.dto.AdminStockItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategory(String category);

    @Query("""
        SELECT new com.lulak.frugo.dto.AdminStockItemDto(
            p.category,
            p.name,
            v
        )
        FROM Product p
        JOIN p.variants v
        WHERE
            (:category IS NULL OR LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%')))
        AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:variant IS NULL OR LOWER(v) LIKE LOWER(CONCAT('%', :variant, '%')))
    """)
    List<AdminStockItemDto> getFilteredStockItems(
            @Param("category") String category,
            @Param("name") String name,
            @Param("variant") String variant
    );
}
