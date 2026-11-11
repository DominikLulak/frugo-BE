package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.warehouse.AdminWarehouseDetailDto;
import com.lulak.frugo.dto.warehouse.AdminWarehouseListDto;
import com.lulak.frugo.dto.warehouse.AdminWarehouseSectorDetailDto;
import com.lulak.frugo.dto.warehouse.CRUD.LocationCreateDto;
import com.lulak.frugo.dto.warehouse.CRUD.WarehouseSectorCreateDto;
import com.lulak.frugo.dto.warehouse.CRUD.warehouse.WarehouseCreateDto;
import com.lulak.frugo.model.warehouse.Location;
import com.lulak.frugo.model.warehouse.Sector;
import com.lulak.frugo.model.warehouse.Warehouse;
import com.lulak.frugo.service.warehouse.AdminWarehouseService;
import com.lulak.frugo.service.warehouse.CRUD.WarehouseCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/warehouses")
public class AdminWarehouseController {

    private final AdminWarehouseService adminWarehouseService;
    private final WarehouseCrudService warehouseCrudService;

    public AdminWarehouseController(
            AdminWarehouseService adminWarehouseService,
            WarehouseCrudService warehouseCrudService){
        this.adminWarehouseService = adminWarehouseService;
        this.warehouseCrudService = warehouseCrudService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminWarehouseListDto> getWarehouses(){
        return adminWarehouseService.getAllWarehouses();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminWarehouseDetailDto getWarehouseDetail(
            @PathVariable Integer id
    ){
        return adminWarehouseService.getWarehouseDetail(id);
    }

    @GetMapping("/sectors/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminWarehouseSectorDetailDto getSector(
            @PathVariable Integer id
    ){
        return adminWarehouseService.getSectorDetail(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_CREATE')")
    public ResponseEntity<Warehouse> createWarehouse(
            @Valid @RequestBody WarehouseCreateDto dto
    ){
        Warehouse warehouse = warehouseCrudService.createWarehouse(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(warehouse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_EDIT')")
    public ResponseEntity<Warehouse> updateWarehouse(
            @PathVariable Integer id,
            @Valid @RequestBody WarehouseCreateDto dto
    ){
        Warehouse warehouse = warehouseCrudService.updateWarehouse(id, dto);

        return ResponseEntity.ok(warehouse);
    }

    @PostMapping("/{warehouseId}/sectors")
    @PreAuthorize("hasAuthority('WAREHOUSE_CREATE')")
    public ResponseEntity<Sector> createWarehouseSector(
            @PathVariable Integer warehouseId,
            @Valid @RequestBody WarehouseSectorCreateDto dto
    ){
        Sector sector = warehouseCrudService.createWarehouseSector(warehouseId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(sector);
    }

    @PutMapping("/{warehouseId}/sectors/{sectorId}")
    @PreAuthorize("hasAuthority('WAREHOUSE_EDIT')")
    public ResponseEntity<Sector> updateWarehouseSector(
            @PathVariable Integer warehouseId,
            @PathVariable Integer sectorId,
            @Valid @RequestBody WarehouseSectorCreateDto dto
    ){
        Sector sector = warehouseCrudService.updateWarehouseSector(sectorId, warehouseId, dto);

        return ResponseEntity.ok(sector);
    }

    @PostMapping("/{sectorId}/locations")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public ResponseEntity<Location> createLocation(
            @PathVariable Integer sectorId,
            @Valid @RequestBody LocationCreateDto dto
    ){
        Location location = warehouseCrudService.createLocation(sectorId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }

    @PutMapping("/locations/{locationId}")
    @PreAuthorize("hasAuthority('WAREHOUSE_EDIT')")
    public ResponseEntity<Location> updateLocation(
            @PathVariable Integer locationId,
            @Valid @RequestBody LocationCreateDto dto
    ){
        Location location = warehouseCrudService.updateLocation(locationId, dto);

        return ResponseEntity.ok(location);
    }
}
