package com.ryvex.client.dto.pcbuild;

import java.math.BigDecimal;

public record HardwareComponentResponse(

        Long id,
        ComponentCategory category,
        String brand,
        String model,
        BigDecimal referencePrice,
        String currencyCode

) {
}