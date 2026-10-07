package com.lulak.frugo.service.referenceData;

import com.lulak.frugo.dto.referenceData.AdminEtiSequenceDto;
import com.lulak.frugo.repository.referenceData.EtiSequenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminEtiSequenceService {

    private final EtiSequenceRepository etiSequenceRepository;

    public AdminEtiSequenceService(
            EtiSequenceRepository etiSequenceRepository
    ){
        this.etiSequenceRepository = etiSequenceRepository;
    }

    public List<AdminEtiSequenceDto> getAllSequences(){ return etiSequenceRepository.getAllSequences(); }
}
