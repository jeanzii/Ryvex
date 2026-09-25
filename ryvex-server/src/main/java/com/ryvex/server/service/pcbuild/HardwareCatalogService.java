package com.ryvex.server.service.pcbuild;

import com.ryvex.server.model.hardware.HardwareComponent;
import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import com.ryvex.server.repository.HardwareComponentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HardwareCatalogService {

    private final HardwareComponentRepository hardwareComponentRepository;

    public HardwareCatalogService(
            HardwareComponentRepository hardwareComponentRepository
    ) {

        this.hardwareComponentRepository =
                hardwareComponentRepository;
    }

    public List<HardwareComponent> getComponents(
            ComponentCategory category
    ) {

        if (
                category == null
        ) {

            return hardwareComponentRepository
                    .findAllByOrderByCategoryAscBrandAscModelAsc();
        }

        return hardwareComponentRepository
                .findAllByCategoryOrderByBrandAscModelAsc(
                        category
                );
    }
}