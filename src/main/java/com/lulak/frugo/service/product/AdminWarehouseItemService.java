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
        return warehouseItemRepository.getFilteredWarehouseItems(
                productCode,
                name,
                category,
                productType,
                etiNumber,
                warehouseCode
        );
    }

    public AdminWarehouseItemDetailDto getWarehouseItemDetail(Integer id){

        AdminWarehouseItemDetailDto warehouseItem =
                warehouseItemRepository.getWarehouseItemDetail(id);

        if(warehouseItem == null){
            throw new RuntimeException(
                    "Warehouse item not found: " + id
            );
        }

        return warehouseItem;
    }
}
