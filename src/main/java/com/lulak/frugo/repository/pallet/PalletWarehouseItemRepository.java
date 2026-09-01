package com.lulak.frugo.repository.pallet;

import com.lulak.frugo.dto.pallet.AdminPalletWarehouseItemDto;
import com.lulak.frugo.model.pallet.PalletWarehouseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PalletWarehouseItemRepository extends JpaRepository<PalletWarehouseItem, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.pallet.AdminPalletWarehouseItemDto(
            wi.product.productCode,
            wi.product.productType.category.code,
            wi.product.productType.name,
            wi.product.name,
            wi.etiNumber,
            wi.quantity
        )
        FROM PalletWarehouseItem pwi
        JOIN pwi.warehouseItem wi
        WHERE pwi.pallet.id = :palletId
    """)
    List<AdminPalletWarehouseItemDto> getPalletItems(
            @Param("palletId") Integer palletId
    );
}
