package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.AdminStockItemDto;
import com.lulak.frugo.repository.ProductRepository;
import com.lulak.frugo.service.AdminProductService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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

    @GetMapping("/stock")
    public List<AdminStockItemDto> getStockItems(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String variant
    ){
        return adminProductService.getFilteredStockItems(category, name, variant);
    }
}
