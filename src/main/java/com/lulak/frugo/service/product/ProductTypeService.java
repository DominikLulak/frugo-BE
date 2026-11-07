package com.lulak.frugo.service.product;

import com.lulak.frugo.dto.product.ProductTypeDto;
import com.lulak.frugo.repository.product.ProductTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeService(
            ProductTypeRepository productTypeRepository
    ){
        this.productTypeRepository = productTypeRepository;
    }

    public List<ProductTypeDto> getProductTypesByCategory(Integer categoryId){
        return productTypeRepository.findByCategory_Id(categoryId)
                .stream()
                .map(type -> new ProductTypeDto(
                        type.getId(),
                        type.getName()
                ))
                .toList();
    }
}
