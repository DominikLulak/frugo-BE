package com.lulak.frugo.dto.product.transactions;

import javax.validation.constraints.NotBlank;

public record MoveStockItemDto(
        @NotBlank
        String eti,

        @NotBlank
        String targetLocationCode
){}
