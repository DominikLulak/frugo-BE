package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.customer.AdminCustomerDetailDto;
import com.lulak.frugo.dto.customer.AdminCustomerListDto;
import com.lulak.frugo.dto.product.AdminProductDetailDto;
import com.lulak.frugo.dto.product.AdminProductListDto;
import com.lulak.frugo.model.product.Product;
import com.lulak.frugo.service.customer.AdminCustomerService;
import com.lulak.frugo.dto.AdminStockItemDto;
import com.lulak.frugo.service.product.AdminProductService;
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
