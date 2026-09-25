package com.ryvex.server.service.pcbuild;

import com.ryvex.server.model.PcBuild;
import com.ryvex.server.model.User;
import com.ryvex.server.model.hardware.*;
import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import com.ryvex.server.repository.HardwareComponentRepository;
import com.ryvex.server.repository.PcBuildRepository;
import com.ryvex.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PcBuildService {

    private final PcBuildRepository pcBuildRepository;
    private final UserRepository userRepository;
    private final HardwareComponentRepository hardwareComponentRepository;

    public PcBuildService(
            PcBuildRepository pcBuildRepository,
            UserRepository userRepository,
            HardwareComponentRepository hardwareComponentRepository
    ) {

        this.pcBuildRepository =
                pcBuildRepository;

        this.userRepository =
                userRepository;

        this.hardwareComponentRepository =
                hardwareComponentRepository;
    }

    public PcBuild createBuild(
            Long userId,
            String name
    ) {

        User user =
                userRepository
                        .findById(
                                userId
                        )
                        .orElseThrow(
                                () ->
                                        new IllegalStateException(
                                                "Authenticated user could not be found."
                                        )
                        );

        PcBuild build =
                new PcBuild(
                        user,
                        normalizeBuildName(
                                name
                        )
                );

        return pcBuildRepository.save(
                build
        );
    }

    @Transactional(readOnly = true)
    public List<PcBuild> getBuilds(
            Long userId
    ) {

        return pcBuildRepository
                .findAllByUser_IdOrderByUpdatedAtDesc(
                        userId
                );
    }

    @Transactional(readOnly = true)
    public PcBuild getBuild(
            Long userId,
            Long buildId
    ) {

        return pcBuildRepository
                .findByIdAndUser_Id(
                        buildId,
                        userId
                )
                .orElseThrow(
                        () ->
                                new PcBuildNotFoundException(
                                        buildId
                                )
                );
    }

    public PcBuild renameBuild(
            Long userId,
            Long buildId,
            String name
    ) {

        PcBuild build =
                getBuild(
                        userId,
                        buildId
                );

        build.setName(
                normalizeBuildName(
                        name
                )
        );

        return pcBuildRepository.save(
                build
        );
    }

    public void deleteBuild(
            Long userId,
            Long buildId
    ) {

        PcBuild build =
                getBuild(
                        userId,
                        buildId
                );

        pcBuildRepository.delete(
                build
        );
    }

    public PcBuild assignComponent(
            Long userId,
            Long buildId,
            ComponentCategory category,
            Long componentId
    ) {

        PcBuild build =
                getBuild(
                        userId,
                        buildId
                );

        HardwareComponent component =
                hardwareComponentRepository
                        .findById(
                                componentId
                        )
                        .orElseThrow(
                                () ->
                                        new HardwareComponentNotFoundException(
                                                componentId
                                        )
                        );

        if (
                component.getCategory()
                        != category
        ) {

            throw new IllegalArgumentException(
                    "Component "
                            + componentId
                            + " belongs to category "
                            + component.getCategory()
                            + ", not "
                            + category
                            + "."
            );
        }

        switch (category) {

            case CPU ->
                    build.setCpu(
                            requireType(
                                    component,
                                    Cpu.class
                            )
                    );

            case MOTHERBOARD ->
                    build.setMotherboard(
                            requireType(
                                    component,
                                    Motherboard.class
                            )
                    );

            case CPU_COOLER ->
                    build.setCpuCooler(
                            requireType(
                                    component,
                                    CpuCooler.class
                            )
                    );

            case MEMORY ->
                    build.setMemory(
                            requireType(
                                    component,
                                    Memory.class
                            )
                    );

            case GPU ->
                    build.setGpu(
                            requireType(
                                    component,
                                    Gpu.class
                            )
                    );

            case STORAGE ->
                    build.setStorage(
                            requireType(
                                    component,
                                    Storage.class
                            )
                    );

            case POWER_SUPPLY ->
                    build.setPowerSupply(
                            requireType(
                                    component,
                                    PowerSupply.class
                            )
                    );

            case CASE ->
                    build.setPcCase(
                            requireType(
                                    component,
                                    PcCase.class
                            )
                    );
        }

        return pcBuildRepository.save(
                build
        );
    }

    public PcBuild removeComponent(
            Long userId,
            Long buildId,
            ComponentCategory category
    ) {

        PcBuild build =
                getBuild(
                        userId,
                        buildId
                );

        switch (category) {

            case CPU ->
                    build.setCpu(
                            null
                    );

            case MOTHERBOARD ->
                    build.setMotherboard(
                            null
                    );

            case CPU_COOLER ->
                    build.setCpuCooler(
                            null
                    );

            case MEMORY ->
                    build.setMemory(
                            null
                    );

            case GPU ->
                    build.setGpu(
                            null
                    );

            case STORAGE ->
                    build.setStorage(
                            null
                    );

            case POWER_SUPPLY ->
                    build.setPowerSupply(
                            null
                    );

            case CASE ->
                    build.setPcCase(
                            null
                    );
        }

        return pcBuildRepository.save(
                build
        );
    }

    @Transactional(readOnly = true)
    public long countBuilds(
            Long userId
    ) {

        return pcBuildRepository
                .countByUser_Id(
                        userId
                );
    }

    private String normalizeBuildName(
            String name
    ) {

        if (
                name == null
                        || name.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "Build name cannot be empty."
            );
        }

        String normalized =
                name.trim();

        if (
                normalized.length()
                        > 100
        ) {

            throw new IllegalArgumentException(
                    "Build name cannot exceed 100 characters."
            );
        }

        return normalized;
    }

    private <T extends HardwareComponent> T requireType(
            HardwareComponent component,
            Class<T> expectedType
    ) {

        if (
                !expectedType.isInstance(
                        component
                )
        ) {

            throw new IllegalArgumentException(
                    "Hardware component "
                            + component.getId()
                            + " has an invalid component type."
            );
        }

        return expectedType.cast(
                component
        );
    }
}