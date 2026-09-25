package com.ryvex.server.controller;

import com.ryvex.server.dto.pcbuild.HardwareComponentResponse;
import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import com.ryvex.server.service.pcbuild.HardwareCatalogService;
import com.ryvex.server.service.pcbuild.PcBuildMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        "/api/hardware"
)
public class HardwareController {

    private final HardwareCatalogService hardwareCatalogService;
    private final PcBuildMapper pcBuildMapper;

    public HardwareController(
            HardwareCatalogService hardwareCatalogService,
            PcBuildMapper pcBuildMapper
    ) {

        this.hardwareCatalogService =
                hardwareCatalogService;

        this.pcBuildMapper =
                pcBuildMapper;
    }

    @GetMapping
    public List<HardwareComponentResponse> getHardware(
            @RequestParam(
                    required = false
            )
            ComponentCategory category
    ) {

        return hardwareCatalogService
                .getComponents(
                        category
                )
                .stream()
                .map(
                        pcBuildMapper::toHardwareResponse
                )
                .toList();
    }
}