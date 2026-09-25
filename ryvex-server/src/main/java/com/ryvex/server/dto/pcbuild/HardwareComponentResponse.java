package com.ryvex.server.dto.pcbuild;

import com.ryvex.server.model.hardware.enumtype.ComponentCategory;

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