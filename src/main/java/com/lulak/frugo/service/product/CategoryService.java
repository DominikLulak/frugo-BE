package com.lulak.frugo.service.product;

import com.lulak.frugo.model.product.Category;
import com.lulak.frugo.repository.product.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository){
        this.repository = repository;
    }

    public List<Category> getAll(){
        return repository.findAll();
    }
}
