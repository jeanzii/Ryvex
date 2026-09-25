package com.ryvex.server.dto.pcbuild;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePcBuildRequest(

        @NotBlank(
                message = "Build name cannot be empty."
        )
        @Size(
                max = 100,
                message = "Build name cannot exceed 100 characters."
        )
        String name

) {
}