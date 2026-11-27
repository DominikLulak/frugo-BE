package com.lulak.frugo.dto.product.transactions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record SplitStockItemDto(
        @NotBlank
        String eti,

        @NotBlank
        @Positive
        Integer quantity
) {
}
