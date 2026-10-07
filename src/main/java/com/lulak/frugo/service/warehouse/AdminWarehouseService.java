package com.lulak.frugo.service.warehouse;

import com.lulak.frugo.dto.warehouse.*;
import com.lulak.frugo.model.warehouse.Sector;
import com.lulak.frugo.model.warehouse.Warehouse;
import com.lulak.frugo.repository.warehouse.SectorRepository;
import com.lulak.frugo.repository.warehouse.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminWarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final SectorRepository sectorRepository;

    public AdminWarehouseService(
            WarehouseRepository warehouseRepository,
            SectorRepository sectorRepository
    ){
        this.warehouseRepository = warehouseRepository;
        this.sectorRepository = sectorRepository;
    }

    public List<AdminWarehouseListDto> getAllWarehouses(){
        return warehouseRepository.getAllWarehouses();
    }

    public AdminWarehouseDetailDto getWarehouseDetail(Integer id){

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found!"));

        List<AdminWarehouseSectorListDto> sectors =
                warehouseRepository.getWarehouseSectors(id);

        return new AdminWarehouseDetailDto(
                warehouse.getId(),
                warehouse.getCode(),
                warehouse.getName(),
                warehouse.getDescription(),
                sectors
        );
    }

    public AdminWarehouseSectorDetailDto getSectorDetail(Integer sectorId){
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new RuntimeException("Sector not found!"));

        List<AdminWarehouseLocationListDto> locations =
                warehouseRepository.getSectorLocations(sectorId);

        return new AdminWarehouseSectorDetailDto(
                sector.getId(),
                sector.getWarehouse().getCode(),
                sector.getCode(),
                sector.getName(),
                sector.getType().getCode(),
                sector.getType().getName(),
                sector.getDescription(),
                locations
        );
    }
}
