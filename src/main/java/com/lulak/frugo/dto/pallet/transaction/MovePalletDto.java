package com.lulak.frugo.dto.pallet.transaction;

import javax.validation.constraints.NotBlank;

public record MovePalletDto(
        @NotBlank
        String palletEti,

        @NotBlank
        String targetLocationCode
) {
}
