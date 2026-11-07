package com.lulak.frugo.service.warehouse.CRUD;

import com.lulak.frugo.dto.warehouse.CRUD.sectorType.SectorTypeCreateDto;
import com.lulak.frugo.model.warehouse.SectorType;
import com.lulak.frugo.repository.warehouse.SectorTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SectorTypeCrudService {

    private final SectorTypeRepository sectorTypeRepository;

    public SectorTypeCrudService(
            SectorTypeRepository sectorTypeRepository
    ){
        this.sectorTypeRepository = sectorTypeRepository;
    }

    @Transactional
    public SectorType createSectorType(
            SectorTypeCreateDto dto
    ){
        if(sectorTypeRepository.existsByCode(dto.getCode())){
            throw new RuntimeException("Sector Type code already exists");
        }
        if(sectorTypeRepository.existsByName(dto.getName())){
            throw new RuntimeException("Sector Type name already exists");
        }

        SectorType sectorType = new SectorType();

        sectorType.setCode(dto.getCode());
        sectorType.setName(dto.getName());
        sectorType.setDescription(dto.getDescription());

        sectorType = sectorTypeRepository.save(sectorType);
        return sectorType;
    }

    @Transactional
    public SectorType updateSectorType(
            Integer sectorTypeId,
            SectorTypeCreateDto dto
    ){
        SectorType sectorType = sectorTypeRepository.findById(sectorTypeId)
                .orElseThrow(() -> new RuntimeException("Sector Type not found " + sectorTypeId));
        if(!sectorType.getCode().equals(dto.getCode())
            && sectorTypeRepository.existsByCodeAndIdNot((dto.getCode()), sectorTypeId)){
            throw new RuntimeException("Sector Type code already exists!");
        }
        if(!sectorType.getName().equals(dto.getName())
                && sectorTypeRepository.existsByNameAndIdNot((dto.getName()), sectorTypeId)){
            throw new RuntimeException("Sector Type name already exists!");
        }

        sectorType.setCode(dto.getCode());
        sectorType.setName(dto.getName());
        sectorType.setDescription(dto.getDescription());

        return sectorTypeRepository.save(sectorType);
    }
}
