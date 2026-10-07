package com.lulak.frugo.service.warehouse;

import com.lulak.frugo.dto.warehouse.AdminWarehouseSectorTypeDto;
import com.lulak.frugo.repository.warehouse.SectorTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSectorTypeService {

    private final SectorTypeRepository sectorTypeRepository;

    public AdminSectorTypeService(
            SectorTypeRepository sectorTypeRepository
    ){
        this.sectorTypeRepository = sectorTypeRepository;
    }

    public List<AdminWarehouseSectorTypeDto> getAllSectorTypes(){ return sectorTypeRepository.getAllSectorTypes(); }
}
