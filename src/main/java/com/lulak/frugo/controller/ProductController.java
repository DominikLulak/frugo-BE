package com.lulak.frugo.controller;

import com.lulak.frugo.model.Product;
import com.lulak.frugo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping
    public List<Product> getProducts(
            @RequestParam(defaultValue = "all") String category
    ){
        return service.getByCategory(category);
    }

    @GetMapping("/categories")
    public List<String> getCategories(){
        return service.getCategories();
    }
}
