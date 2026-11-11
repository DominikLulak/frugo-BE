package com.lulak.frugo.service.referenceData.CRUD;

import com.lulak.frugo.dto.referenceData.CRUD.ShiftCreateDto;
import com.lulak.frugo.model.employee.Shift;
import com.lulak.frugo.repository.referenceData.ShiftRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ShiftCrudService {

    private final ShiftRepository shiftRepository;

    public ShiftCrudService(
            ShiftRepository shiftRepository
    ){
        this.shiftRepository = shiftRepository;
    }

    @Transactional
    public Shift createShift(
            ShiftCreateDto dto
    ){
        Shift shift = new Shift();

        shift.setCode(dto.getCode());
        shift.setDescription(dto.getDescription());

        shift = shiftRepository.save(shift);
        return shift;
    }

    @Transactional
    public Shift updateShift(
            Integer shiftId,
            ShiftCreateDto dto
    ){
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found " + shiftId));

        shift.setCode(dto.getCode());
        shift.setDescription(dto.getDescription());

        return shiftRepository.save(shift);
    }
}
