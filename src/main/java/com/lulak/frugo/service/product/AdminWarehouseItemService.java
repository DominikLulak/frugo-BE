package com.lulak.frugo.service.product;

import com.lulak.frugo.dto.product.AdminWarehouseItemDetailDto;
import com.lulak.frugo.dto.product.AdminWarehouseItemListDto;
import com.lulak.frugo.model.product.WarehouseItem;
import com.lulak.frugo.repository.product.WarehouseItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminWarehouseItemService {

    private final WarehouseItemRepository warehouseItemRepository;

    public AdminWarehouseItemService(
            WarehouseItemRepository warehouseItemRepository
    ){
        this.warehouseItemRepository = warehouseItemRepository;
    }

    public List<AdminWarehouseItemListDto> getFilteredWarehouseItems(
            String category,
            String productType,
            String name,
            String productCode,
            String etiNumber,
            String warehouseCode
    ){
        List<WarehouseItem> warehouseItems =
                warehouseItemRepository.getFilteredWarehouseItems(
                        category,
                        productType,
                        name,
                        productCode,
                        etiNumber,
                        warehouseCode
                );
            return warehouseItems.stream()
                    .map(this::toListDto)
                    .toList();
    }

    private AdminWarehouseItemListDto toListDto(
            WarehouseItem item
    ){
        return new AdminWarehouseItemListDto(
                item.getId(),
                item.getProduct()
                        .getProductType()
                        .getCategory()
                        .getCode(),
                item.getProduct()
                        .getProductType()
                        .getName(),
                item.getProduct()
                        .getName(),
                item.getProduct()
                        .getProductCode(),
                item.getEtiNumber(),
                item.getQuantity(),
                item.getAllocatedQuantity(),
                item.getQuantity() - item.getAllocatedQuantity(),
                item.getExpirationDate(),
                item.getLocation()
                        .getSector()
                        .getWarehouse()
                        .getCode(),
                item.getLocation()
                        .getCode()
        );
    }

    public AdminWarehouseItemDetailDto getWarehouseItemDetail(Integer id){

        WarehouseItem item = warehouseItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Warehouse item not found: " + id)
                );

        return toDetailDto(item);
    }

    private AdminWarehouseItemDetailDto toDetailDto(
            WarehouseItem item
    ){
        return new AdminWarehouseItemDetailDto(
                item.getId(),
                item.getProduct()
                        .getProductType()
                        .getCategory()
                        .getCode(),
                item.getProduct()
                        .getProductType()
                        .getName(),
                item.getProduct()
                        .getName(),
                item.getProduct()
                        .getProductCode(),
                item.getEtiNumber(),
                item.getPackagingType()
                        .getCode(),
                item.getQuantityPerPackage(),
                item.getSubpackageType() != null
                        ?item.getSubpackageType().getName()
                        : null,
                item.getQuantityPerSubpackage(),
                item.getQuantity(),
                item.getAllocatedQuantity(),
                item.getQuantity() - item.getAllocatedQuantity(),
                item.getCountryOfOrigin()
                        .getCode(),
                item.getReceivedAt(),
                item.getExpirationDate(),
                item.getLocation()
                        .getSector()
                        .getWarehouse()
                        .getCode(),
                item.getLocation()
                        .getCode()
        );
    }
}
