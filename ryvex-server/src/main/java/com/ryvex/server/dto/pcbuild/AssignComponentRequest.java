package com.ryvex.server.dto.pcbuild;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AssignComponentRequest(

        @NotNull(
                message = "Component ID is required."
        )
        @Positive(
                message = "Component ID must be positive."
        )
        Long componentId

) {
}