package com.lulak.frugo.repository.product;

import com.lulak.frugo.model.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

    Optional<ProductType> findByCategory_IdAndNameIgnoreCase(
            Integer categoryId,
            String name
    );

    List<ProductType> findByCategory_Id(Integer categoryId);
}
