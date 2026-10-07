package com.lulak.frugo.service.referenceData;

import com.lulak.frugo.dto.referenceData.AdminStatusDto;
import com.lulak.frugo.repository.referenceData.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminStatusService {

    private final StatusRepository statusRepository;

    public AdminStatusService(
            StatusRepository statusRepository
    ){
        this.statusRepository = statusRepository;
    }

    public List<AdminStatusDto> getAllStatuses(){ return statusRepository.getAllStatuses(); }
}
