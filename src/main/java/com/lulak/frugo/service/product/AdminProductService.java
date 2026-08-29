package com.lulak.frugo.service.product;

import com.lulak.frugo.dto.AdminStockItemDto;
import com.lulak.frugo.dto.product.AdminProductListDto;
import com.lulak.frugo.model.product.Product;
import com.lulak.frugo.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductService {

    private final ProductRepository productRepository;

    public AdminProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<AdminProductListDto> getFilteredProducts(
            String category,
            String name,
            String productType,
            String productCode
    ){
        return productRepository.getFilteredProducts(
                category,
                name,
                productType,
                productCode
        );
    }

    public Product getProduct(Integer id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));
    }
}
