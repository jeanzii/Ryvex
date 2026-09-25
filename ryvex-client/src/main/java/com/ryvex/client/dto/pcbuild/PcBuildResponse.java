package com.ryvex.client.dto.pcbuild;

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

        String createdAt,
        String updatedAt

) {
}