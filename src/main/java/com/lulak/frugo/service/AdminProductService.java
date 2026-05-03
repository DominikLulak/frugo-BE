package com.lulak.frugo.service;

import com.lulak.frugo.controller.admin.AdminProductController;
import com.lulak.frugo.dto.AdminStockItemDto;
import com.lulak.frugo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductService {

    private final ProductRepository productRepository;

    public AdminProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<AdminStockItemDto> getFilteredStockItems(
            String category,
            String name,
            String variant
    ){
        return productRepository.getFilteredStockItems(
                category,
                name,
                variant
        );
    }
}
