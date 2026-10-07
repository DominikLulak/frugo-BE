package com.lulak.frugo.repository.product;

import com.lulak.frugo.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
