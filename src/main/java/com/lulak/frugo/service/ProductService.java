package com.lulak.frugo.service;

import com.lulak.frugo.model.product.Product;
import com.lulak.frugo.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

   private final ProductRepository repository;

   public ProductService(ProductRepository repository){
       this.repository = repository;
   }

   public List<Product> getAll(){
       return repository.findAll();
   }

   public List<Product> getByCategory(String category){
       if(category.equals("all")){
           return repository.findAll();
       }

       return repository.findByProductType_Category_Code(category);
   }
}
