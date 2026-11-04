package com.lulak.frugo.service.referenceData.CRUD;

import com.lulak.frugo.dto.referenceData.CRUD.PackagingCreateDto;
import com.lulak.frugo.model.product.Packaging;
import com.lulak.frugo.repository.product.PackagingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PackagingCrudService {

    private final PackagingRepository packagingRepository;

    public PackagingCrudService(
            PackagingRepository packagingRepository
    ){
        this.packagingRepository = packagingRepository;
    }

    @Transactional
    public Packaging createPackaging(
            PackagingCreateDto dto
    ){
        Packaging packaging = new Packaging();

        packaging.setCode(dto.getCode());
        packaging.setName(dto.getName());
        packaging.setDescription(dto.getDescription());

        packaging = packagingRepository.save(packaging);
        return packaging;
    }

    @Transactional
    public Packaging updatePackaging(
            Integer packagingId,
            PackagingCreateDto dto
    ){
        Packaging packaging = packagingRepository.findById(packagingId)
                .orElseThrow(() -> new RuntimeException("Packaging not found " + packagingId));

        packaging.setCode(dto.getCode());
        packaging.setName(dto.getName());
        packaging.setDescription(dto.getDescription());

        return packagingRepository.save(packaging);
    }
}
