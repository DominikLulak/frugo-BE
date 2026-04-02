package com.lulak.frugo.service;

import com.lulak.frugo.model.Product;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@Service
public class ProductService {

    private List<Product> products;

    public ProductService(){
        loadProducts();
    }

    private void loadProducts(){
        try{
            ObjectMapper mapper = new ObjectMapper();
            InputStream input = new ClassPathResource("products.json").getInputStream();

            products = mapper.readValue(input, new TypeReference<List<Product>>() {});
        } catch (Exception e){
            throw new RuntimeException("Failed to load products", e);
        }
    }

    public List<Product> getAll(){
        return products;
    }

    public List<Product> getByCategory(String category){
        if(category.equals("all")) return products;

        return products.stream()
                .filter(p -> p.getCategory().equals(category))
                .toList();
    }

    public List<String> getCategories(){
        return products.stream()
                .map(Product::getCategory)
                .distinct()
                .toList();
    }
}
