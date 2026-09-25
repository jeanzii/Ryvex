package com.ryvex.server.service.pcbuild;

import com.ryvex.server.dto.pcbuild.HardwareComponentResponse;
import com.ryvex.server.dto.pcbuild.PcBuildResponse;
import com.ryvex.server.model.PcBuild;
import com.ryvex.server.model.hardware.HardwareComponent;
import org.springframework.stereotype.Component;

@Component
public class PcBuildMapper {

    public PcBuildResponse toResponse(
            PcBuild build
    ) {

        return new PcBuildResponse(
                build.getId(),
                build.getVersion(),
                build.getName(),

                toHardwareResponse(
                        build.getCpu()
                ),

                toHardwareResponse(
                        build.getMotherboard()
                ),

                toHardwareResponse(
                        build.getCpuCooler()
                ),

                toHardwareResponse(
                        build.getMemory()
                ),

                toHardwareResponse(
                        build.getGpu()
                ),

                toHardwareResponse(
                        build.getStorage()
                ),

                toHardwareResponse(
                        build.getPowerSupply()
                ),

                toHardwareResponse(
                        build.getPcCase()
                ),

                build.getCreatedAt(),
                build.getUpdatedAt()
        );
    }

    public HardwareComponentResponse toHardwareResponse(
            HardwareComponent component
    ) {

        if (
                component == null
        ) {

            return null;
        }

        return new HardwareComponentResponse(
                component.getId(),
                component.getCategory(),
                component.getBrand(),
                component.getModel(),
                component.getReferencePrice(),
                component.getCurrencyCode()
        );
    }
}