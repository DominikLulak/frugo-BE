package com.lulak.frugo.service.pallet;

import com.lulak.frugo.dto.pallet.AdminPalletWarehouseItemDto;
import com.lulak.frugo.repository.pallet.PalletWarehouseItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPalletService {

    private final PalletWarehouseItemRepository palletWarehouseItemRepository;

    public AdminPalletService(
            PalletWarehouseItemRepository palletWarehouseItemRepository
    ){
        this.palletWarehouseItemRepository = palletWarehouseItemRepository;
    }

    public List<AdminPalletWarehouseItemDto> getPalletItems(
            Integer palletId
    ){
        return palletWarehouseItemRepository.getPalletItems(palletId);
    }
}
