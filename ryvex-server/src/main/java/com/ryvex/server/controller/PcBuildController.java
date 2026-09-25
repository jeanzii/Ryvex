package com.ryvex.server.controller;

import com.ryvex.server.dto.pcbuild.AssignComponentRequest;
import com.ryvex.server.dto.pcbuild.CreatePcBuildRequest;
import com.ryvex.server.dto.pcbuild.PcBuildResponse;
import com.ryvex.server.dto.pcbuild.UpdatePcBuildRequest;
import com.ryvex.server.model.PcBuild;
import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import com.ryvex.server.service.pcbuild.PcBuildMapper;
import com.ryvex.server.service.pcbuild.PcBuildService;
import com.ryvex.server.service.security.CurrentUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        "/api/builds"
)
public class PcBuildController {

    private final PcBuildService pcBuildService;
    private final PcBuildMapper pcBuildMapper;
    private final CurrentUserService currentUserService;

    public PcBuildController(
            PcBuildService pcBuildService,
            PcBuildMapper pcBuildMapper,
            CurrentUserService currentUserService
    ) {

        this.pcBuildService =
                pcBuildService;

        this.pcBuildMapper =
                pcBuildMapper;

        this.currentUserService =
                currentUserService;
    }

    @GetMapping
    public List<PcBuildResponse> getBuilds(
            Authentication authentication
    ) {

        Long userId =
                currentUserService
                        .requireUserId(
                                authentication.getName()
                        );

        return pcBuildService
                .getBuilds(
                        userId
                )
                .stream()
                .map(
                        pcBuildMapper::toResponse
                )
                .toList();
    }

    @PostMapping
    @ResponseStatus(
            HttpStatus.CREATED
    )
    public PcBuildResponse createBuild(
            Authentication authentication,
            @Valid
            @RequestBody
            CreatePcBuildRequest request
    ) {

        Long userId =
                currentUserService
                        .requireUserId(
                                authentication.getName()
                        );

        PcBuild build =
                pcBuildService.createBuild(
                        userId,
                        request.name()
                );

        return pcBuildMapper.toResponse(
                build
        );
    }

    @GetMapping(
            "/{buildId}"
    )
    public PcBuildResponse getBuild(
            Authentication authentication,
            @PathVariable
            Long buildId
    ) {

        Long userId =
                currentUserService
                        .requireUserId(
                                authentication.getName()
                        );

        return pcBuildMapper.toResponse(
                pcBuildService.getBuild(
                        userId,
                        buildId
                )
        );
    }

    @PutMapping(
            "/{buildId}"
    )
    public PcBuildResponse updateBuild(
            Authentication authentication,
            @PathVariable
            Long buildId,
            @Valid
            @RequestBody
            UpdatePcBuildRequest request
    ) {

        Long userId =
                currentUserService
                        .requireUserId(
                                authentication.getName()
                        );

        return pcBuildMapper.toResponse(
                pcBuildService.renameBuild(
                        userId,
                        buildId,
                        request.name()
                )
        );
    }

    @DeleteMapping(
            "/{buildId}"
    )
    @ResponseStatus(
            HttpStatus.NO_CONTENT
    )
    public void deleteBuild(
            Authentication authentication,
            @PathVariable
            Long buildId
    ) {

        Long userId =
                currentUserService
                        .requireUserId(
                                authentication.getName()
                        );

        pcBuildService.deleteBuild(
                userId,
                buildId
        );
    }

    @PutMapping(
            "/{buildId}/components/{category}"
    )
    public PcBuildResponse assignComponent(
            Authentication authentication,
            @PathVariable
            Long buildId,
            @PathVariable
            ComponentCategory category,
            @Valid
            @RequestBody
            AssignComponentRequest request
    ) {

        Long userId =
                currentUserService
                        .requireUserId(
                                authentication.getName()
                        );

        return pcBuildMapper.toResponse(
                pcBuildService.assignComponent(
                        userId,
                        buildId,
                        category,
                        request.componentId()
                )
        );
    }

    @DeleteMapping(
            "/{buildId}/components/{category}"
    )
    public PcBuildResponse removeComponent(
            Authentication authentication,
            @PathVariable
            Long buildId,
            @PathVariable
            ComponentCategory category
    ) {

        Long userId =
                currentUserService
                        .requireUserId(
                                authentication.getName()
                        );

        return pcBuildMapper.toResponse(
                pcBuildService.removeComponent(
                        userId,
                        buildId,
                        category
                )
        );
    }
}