package com.lulak.frugo.service.referenceData;

import com.lulak.frugo.dto.referenceData.AdminShiftDto;
import com.lulak.frugo.repository.referenceData.ShiftRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminShiftService {

    private final ShiftRepository shiftRepository;

    public AdminShiftService(
            ShiftRepository shiftRepository
    ){
        this.shiftRepository = shiftRepository;
    }

    public List<AdminShiftDto> getAllShifts(){ return shiftRepository.getAllShifts(); }
}
