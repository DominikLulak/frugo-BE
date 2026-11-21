package com.lulak.frugo.service.event;

import com.lulak.frugo.dto.event.AdminStockMovementDetailDto;
import com.lulak.frugo.dto.event.AdminStockMovementListDto;
import com.lulak.frugo.repository.event.StockMovementRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMovementQueryService {

    private final StockMovementRepository stockMovementRepository;

    public StockMovementQueryService(
            StockMovementRepository stockMovementRepository
    ){
        this.stockMovementRepository = stockMovementRepository;
    }

    @Transactional(readOnly = true)
    public List<AdminStockMovementListDto> getFilteredStockMovements(
            String eventCode,
            String etiNumber,
            String fromLocation,
            String toLocation,
            String employeeNumber
    ){
        return stockMovementRepository.getFilteredStockMovements(
                eventCode,
                etiNumber,
                fromLocation,
                toLocation,
                employeeNumber
        );
    }

    @Transactional(readOnly = true)
    public AdminStockMovementDetailDto getStockMovementDetail(Long id){
        AdminStockMovementDetailDto stockMovement =
                stockMovementRepository.getStockMovementDetail(id);

        if(stockMovement == null){
            throw new RuntimeException("Stock movement not found!");
        }

        return stockMovement;
    }
}
