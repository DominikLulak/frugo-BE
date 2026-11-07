package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.product.ProductTypeDto;
import com.lulak.frugo.service.product.ProductTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product-types")
@CrossOrigin("*")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService productTypeService){
        this.productTypeService = productTypeService;
    }

    @GetMapping("/category/{categoryId}")
    @PreAuthorize("hasAuthority('PRODUCTS_READ')")
    public ResponseEntity<List<ProductTypeDto>> getByCategory(
            @PathVariable Integer categoryId
    ){
        return ResponseEntity.ok(
                productTypeService.getProductTypesByCategory(categoryId)
        );
    }
}
