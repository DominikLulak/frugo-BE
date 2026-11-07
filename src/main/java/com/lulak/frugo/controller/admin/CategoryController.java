package com.lulak.frugo.controller.admin;

import com.lulak.frugo.model.product.Category;
import com.lulak.frugo.service.product.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(
            CategoryService categoryService
    ){
        this.categoryService = categoryService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCTS_READ')")
    public List<Category> getAllCategories(){
        return categoryService.getAll();
    }
}
