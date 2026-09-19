package com.lulak.frugo.service.product;

import com.lulak.frugo.dto.product.AdminPackagingDto;
import com.lulak.frugo.repository.product.PackagingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPackagingService {

    private final PackagingRepository packagingRepository;

    public AdminPackagingService(
            PackagingRepository packagingRepository
    ){
        this.packagingRepository = packagingRepository;
    }

    public List<AdminPackagingDto> getAllPackaging(){ return packagingRepository.getAllPackaging(); }
}
