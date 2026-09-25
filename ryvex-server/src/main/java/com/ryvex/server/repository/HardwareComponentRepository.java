package com.ryvex.server.repository;

import com.ryvex.server.model.hardware.HardwareComponent;
import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HardwareComponentRepository
        extends JpaRepository<HardwareComponent, Long> {

    List<HardwareComponent> findAllByCategoryOrderByBrandAscModelAsc(
            ComponentCategory category
    );

    List<HardwareComponent> findAllByOrderByCategoryAscBrandAscModelAsc();
}