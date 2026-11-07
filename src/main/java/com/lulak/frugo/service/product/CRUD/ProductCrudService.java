package com.lulak.frugo.service.product.CRUD;

import com.lulak.frugo.dto.product.CRUD.ProductCreateDto;
import com.lulak.frugo.model.product.Category;
import com.lulak.frugo.model.product.Product;
import com.lulak.frugo.model.product.ProductType;
import com.lulak.frugo.repository.product.CategoryRepository;
import com.lulak.frugo.repository.product.ProductRepository;
import com.lulak.frugo.repository.product.ProductTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductCrudService {

    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final CategoryRepository categoryRepository;

    public ProductCrudService(
            ProductRepository productRepository,
            ProductTypeRepository productTypeRepository,
            CategoryRepository categoryRepository
    ){
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.categoryRepository = categoryRepository;
    }


    @Transactional
    public Product createProduct(ProductCreateDto dto){
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found " + dto.getCategoryId()));

        String productCode = dto.getProductCode().trim();

        if(productRepository.existsByProductCode(productCode)){
            throw new RuntimeException("Product code already exists " + productCode);
        }

        ProductType productType = findOrCreateProductType(
                category,
                dto.getProductTypeName()
        );

        Product product = new Product();

        product.setProductType(productType);
        product.setProductCode(productCode);
        product.setName(dto.getName().trim());
        product.setPricePerUnit(dto.getPricePerUnit());
        product.setShelfLifeDays(dto.getShelfLifeDays());
        product.setReorderPoint(dto.getReorderPoint());
        product.setMinimumStock(dto.getMinimumStock());
        product.setForSale(false);

        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(
            Integer productId,
            ProductCreateDto dto
    ){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found " + productId));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found " + dto.getCategoryId()));

        String productCode = dto.getProductCode().trim();

        if(productRepository.existsByProductCodeAndIdNot(productCode, productId)){
            throw new RuntimeException("Product code already exists: " + productCode);
        }

        ProductType productType = findOrCreateProductType(
                category,
                dto.getProductTypeName()
        );

        product.setProductType(productType);
        product.setProductCode(productCode);
        product.setName(dto.getName().trim());
        product.setPricePerUnit(dto.getPricePerUnit());
        product.setShelfLifeDays(dto.getShelfLifeDays());
        product.setReorderPoint(dto.getReorderPoint());
        product.setMinimumStock(dto.getMinimumStock());

        return productRepository.save(product);
    }

    @Transactional
    public Product setProductForSale(
            Integer productId,
            boolean forSale
    ){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found " + productId));

        product.setForSale(forSale);
        return productRepository.save(product);
    }

    private ProductType findOrCreateProductType(
            Category category,
            String productTypeName
    ){
        String normalizedName = productTypeName.trim();

        return productTypeRepository
                .findByCategory_IdAndNameIgnoreCase(
                        category.getId(),
                        normalizedName
                )
                .orElseGet(() -> {
                    ProductType newProductType = new ProductType();

                    newProductType.setCategory(category);
                    newProductType.setName(normalizedName);

                    return productTypeRepository.save(newProductType);
                });
    }
}
