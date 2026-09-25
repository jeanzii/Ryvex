package com.ryvex.server.dto.pcbuild;

import java.time.Instant;

public record PcBuildResponse(

        Long id,
        Long version,
        String name,

        HardwareComponentResponse cpu,
        HardwareComponentResponse motherboard,
        HardwareComponentResponse cpuCooler,
        HardwareComponentResponse memory,
        HardwareComponentResponse gpu,
        HardwareComponentResponse storage,
        HardwareComponentResponse powerSupply,
        HardwareComponentResponse pcCase,

        Instant createdAt,
        Instant updatedAt

) {
}