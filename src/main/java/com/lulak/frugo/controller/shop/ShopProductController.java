package com.lulak.frugo.controller.shop;

import com.lulak.frugo.model.Product;
import com.lulak.frugo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop/products")
@CrossOrigin(origins = "*")
public class ShopProductController {

    private final ProductService service;

    public ShopProductController(ProductService service){
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
