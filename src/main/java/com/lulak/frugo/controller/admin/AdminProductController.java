package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.product.AdminProductDetailDto;
import com.lulak.frugo.dto.product.AdminProductListDto;
import com.lulak.frugo.model.product.Product;
import com.lulak.frugo.service.product.AdminProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin("*")
public class AdminProductController {

    private final AdminProductService adminProductService;

    public AdminProductController(AdminProductService adminProductService){
        this.adminProductService = adminProductService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public List<AdminProductListDto> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String productType,
            @RequestParam(required = false) String productCode
    ){
        return adminProductService.getFilteredProducts(
                category,
                name,
                productType,
                productCode
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public AdminProductDetailDto getProductDetail(
            @PathVariable Integer id
    ){
        Product product = adminProductService.getProduct(id);

        return new AdminProductDetailDto(
                product.getId(),
                product.getProductCode(),
                product.getName(),
                product.getProductType().getName(),
                product.getProductType().getCategory().getName(),
                product.getPricePerUnit(),
                product.getShelfLifeDays(),
                product.isForSale()
        );
    }
}
