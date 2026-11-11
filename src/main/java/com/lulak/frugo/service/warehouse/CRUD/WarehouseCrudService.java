package com.lulak.frugo.service.warehouse.CRUD;

import com.lulak.frugo.dto.warehouse.CRUD.LocationCreateDto;
import com.lulak.frugo.dto.warehouse.CRUD.WarehouseSectorCreateDto;
import com.lulak.frugo.dto.warehouse.CRUD.warehouse.WarehouseCreateDto;
import com.lulak.frugo.model.warehouse.Location;
import com.lulak.frugo.model.warehouse.Sector;
import com.lulak.frugo.model.warehouse.SectorType;
import com.lulak.frugo.model.warehouse.Warehouse;
import com.lulak.frugo.repository.warehouse.LocationRepository;
import com.lulak.frugo.repository.warehouse.SectorRepository;
import com.lulak.frugo.repository.warehouse.SectorTypeRepository;
import com.lulak.frugo.repository.warehouse.WarehouseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WarehouseCrudService {

    private final WarehouseRepository warehouseRepository;
    private final SectorRepository sectorRepository;
    private final SectorTypeRepository sectorTypeRepository;
    private final LocationRepository locationRepository;

    public WarehouseCrudService(
            WarehouseRepository warehouseRepository,
            SectorRepository sectorRepository,
            SectorTypeRepository sectorTypeRepository,
            LocationRepository locationRepository
    ){
        this.warehouseRepository = warehouseRepository;
        this.sectorRepository = sectorRepository;
        this.sectorTypeRepository = sectorTypeRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional
    public Warehouse createWarehouse(
            WarehouseCreateDto dto
    ){
        if(warehouseRepository.existsByCode(dto.getCode())){
            throw new RuntimeException("Warehouse code already exists");
        }
        if(warehouseRepository.existsByName(dto.getName())){
            throw new RuntimeException("Warehouse name already exists");
        }

        Warehouse warehouse = new Warehouse();

        warehouse.setCode(dto.getCode());
        warehouse.setName(dto.getName());
        warehouse.setDescription(dto.getDescription());

        warehouse = warehouseRepository.save(warehouse);
        return warehouse;
    }

    @Transactional
    public Warehouse updateWarehouse(
            Integer warehouseId,
            WarehouseCreateDto dto
    ){
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found " + warehouseId));
        if(!warehouse.getCode().equals(dto.getCode())
            && warehouseRepository.existsByCodeAndIdNot((dto.getCode()), warehouseId)){
            throw new RuntimeException("Warehouse code already exists!");
        }
        if(!warehouse.getName().equals(dto.getName())
                && warehouseRepository.existsByNameAndIdNot((dto.getName()), warehouseId)){
            throw new RuntimeException("Warehouse name already exists!");
        }

        warehouse.setCode(dto.getCode());
        warehouse.setName(dto.getName());
        warehouse.setDescription(dto.getDescription());

        return warehouseRepository.save(warehouse);
    }

    @Transactional
    public Sector createWarehouseSector(
            Integer warehouseId,
            WarehouseSectorCreateDto dto
    ){
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        SectorType sectorType = sectorTypeRepository.findById(dto.getSectorTypeId())
                .orElseThrow(() -> new RuntimeException("Sector type not found"));

        if(sectorRepository.existsByCodeAndWarehouseId(dto.getCode(), warehouseId)){
            throw new RuntimeException("Sector code already exists fot this warehouse");
        }
        if(sectorRepository.existsByNameAndWarehouseId(dto.getName(), warehouseId)){
            throw new RuntimeException("Sector name already exists fot this warehouse");
        }

        Sector sector = new Sector();

        sector.setWarehouse(warehouse);
        sector.setCode(dto.getCode());
        sector.setName(dto.getName());
        sector.setType(sectorType);
        sector.setDescription(dto.getDescription());

        sector = sectorRepository.save(sector);
        return sector;
    }

    @Transactional
    public Sector updateWarehouseSector(
            Integer sectorId,
            Integer warehouseId,
            WarehouseSectorCreateDto dto
    ){
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new RuntimeException("Sector not found " + sectorId));

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found " + warehouseId));

        SectorType sectorType = sectorTypeRepository.findById(dto.getSectorTypeId())
                .orElseThrow(() -> new RuntimeException("Sector type not found"));

        if(sectorRepository.existsByCodeAndWarehouseIdAndIdNot(
                dto.getCode(), warehouseId, sectorId
        )){
            throw new RuntimeException("Sector code already exists for this warehouse");
        }
        if(sectorRepository.existsByNameAndWarehouseIdAndIdNot(
                dto.getName(), warehouseId, sectorId
        )){
            throw new RuntimeException("Sector name already exists for this warehouse");
        }

        sector.setCode(dto.getCode());
        sector.setName(dto.getName());
        sector.setType(sectorType);
        sector.setDescription(dto.getDescription());

        return sectorRepository.save(sector);
    }

    @Transactional
    public Location createLocation(
            Integer sectorId,
            LocationCreateDto dto
    ){
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new RuntimeException("Sector not found"));

        Location location = new Location();

        location.setCode(dto.getCode());
        location.setSector(sector);
        location.setAisle(dto.getAisle());
        location.setRack(dto.getRack());
        location.setLevel(dto.getLevel());
        location.setPosition(dto.getPosition());
        location.setCanBeOrdered(dto.isCanBeOrdered());

        location = locationRepository.save(location);
        return location;
    }

    @Transactional
    public Location updateLocation(
            Integer locationId,
            LocationCreateDto dto
    ){
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found " + locationId));

        location.setCode(dto.getCode());
        location.setAisle(dto.getAisle());
        location.setRack(dto.getRack());
        location.setLevel(dto.getLevel());
        location.setPosition(dto.getPosition());
        location.setCanBeOrdered(dto.isCanBeOrdered());

        return locationRepository.save(location);
    }
}
