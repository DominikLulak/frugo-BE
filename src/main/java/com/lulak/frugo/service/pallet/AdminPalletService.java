package com.lulak.frugo.service.pallet;

import com.lulak.frugo.dto.pallet.AdminPalletDetailDto;
import com.lulak.frugo.dto.pallet.AdminPalletListDto;
import com.lulak.frugo.dto.pallet.AdminPalletWarehouseItemDto;
import com.lulak.frugo.model.pallet.Pallet;
import com.lulak.frugo.repository.pallet.PalletRepository;
import com.lulak.frugo.repository.pallet.PalletWarehouseItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPalletService {

    private final PalletRepository palletRepository;
    private final PalletWarehouseItemRepository palletWarehouseItemRepository;

    public AdminPalletService(
            PalletRepository palletRepository,
            PalletWarehouseItemRepository palletWarehouseItemRepository
    ){
        this.palletRepository = palletRepository;
        this.palletWarehouseItemRepository = palletWarehouseItemRepository;
    }

    public List<AdminPalletListDto> getFilteredPallets(
            String etiNumber,
            String locationCode,
            Boolean isClosed
    ){
        return palletRepository.getFilteredPallets(
                etiNumber,
                locationCode,
                isClosed
        );
    }

    public AdminPalletDetailDto getPalletDetail(Integer palletId){
        Pallet pallet = palletRepository.findById(palletId)
                .orElseThrow(() -> new RuntimeException("Pallet not found " + palletId));

        List<AdminPalletWarehouseItemDto> items =
                palletWarehouseItemRepository.findByPalletId(palletId)
                        .stream()
                        .map(item -> new AdminPalletWarehouseItemDto(
                                item.getProductCode(),
                                item.getCategoryCode(),
                                item.getProductType(),
                                item.getProductName(),
                                item.getEtiNumber(),
                                item.getQuantity()
                        ))
                        .toList();

        return new AdminPalletDetailDto(
                pallet.getId(),
                pallet.getPalletNumber(),
                pallet.getLocation().getCode(),
                pallet.isClosed(),
                items
        );
    }
}
