package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.product.AdminProductDetailDto;
import com.lulak.frugo.dto.product.AdminProductListDto;
import com.lulak.frugo.dto.product.CRUD.ProductCreateDto;
import com.lulak.frugo.dto.product.CRUD.ProductSaleStatusDto;
import com.lulak.frugo.model.product.Product;
import com.lulak.frugo.service.product.AdminProductService;
import com.lulak.frugo.service.product.CRUD.ProductCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin("*")
public class AdminProductController {

    private final AdminProductService adminProductService;
    private final ProductCrudService productCrudService;

    public AdminProductController(
            AdminProductService adminProductService,
            ProductCrudService productCrudService
    ){
        this.adminProductService = adminProductService;
        this.productCrudService = productCrudService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCTS_READ')")
    public List<AdminProductListDto> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String productType,
            @RequestParam(required = false) String productCode
    ){
        return adminProductService.getFilteredProducts(
                category,
                productType,
                productCode
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCTS_READ')")
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

    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCTS_CREATE')")
    public ResponseEntity<Product> createProduct(
            @Valid @RequestBody ProductCreateDto dto
    ){
        Product product = productCrudService.createProduct(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('PRODUCTS_EDIT')")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Integer productId,
            @Valid @RequestBody ProductCreateDto dto
    ){
        Product product = productCrudService.updateProduct(productId, dto);

        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{productId}/saleStatus")
    @PreAuthorize("hasAuthority('PRODUCTS_SALE')")
    public ResponseEntity<Product> setProductForSale(
            @PathVariable Integer productId,
            @Valid @RequestBody ProductSaleStatusDto dto
    ){
        Product product = productCrudService.setProductForSale(productId, dto.getForSale());

        return ResponseEntity.ok(product);
    }
}
