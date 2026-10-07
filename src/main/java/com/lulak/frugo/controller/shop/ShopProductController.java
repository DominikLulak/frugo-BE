package com.lulak.frugo.controller.shop;

import com.lulak.frugo.model.product.Category;
import com.lulak.frugo.model.product.Product;
import com.lulak.frugo.service.product.CategoryService;
import com.lulak.frugo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop/products")
@CrossOrigin(origins = "*")
public class ShopProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ShopProductController(
            ProductService productService,
            CategoryService categoryService
    ){
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Product> getProducts(
            @RequestParam(defaultValue = "all") String category
    ){
        return productService.getByCategory(category);
    }

    @GetMapping("/categories")
    public List<Category> getCategories(){
        return categoryService.getAll();
    }
}
