package com.lulak.frugo.service.event;

import com.lulak.frugo.dto.event.AdminStockMovementDetailDto;
import com.lulak.frugo.dto.event.AdminStockMovementListDto;
import com.lulak.frugo.repository.event.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminStockMovementService {

    private final StockMovementRepository stockMovementRepository;

    public AdminStockMovementService(
            StockMovementRepository stockMovementRepository
    ){
        this.stockMovementRepository = stockMovementRepository;
    }

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

    public AdminStockMovementDetailDto getStockMovementDetail(Integer id){
        AdminStockMovementDetailDto stockMovement =
                stockMovementRepository.getStockMovementDetail(id);

        if(stockMovement == null){
            throw new RuntimeException("Stock movement not found!");
        }

        return stockMovement;
    }
}
