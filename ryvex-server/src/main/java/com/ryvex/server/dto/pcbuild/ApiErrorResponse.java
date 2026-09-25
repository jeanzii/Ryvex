package com.ryvex.server.dto.pcbuild;

import java.time.Instant;

public record ApiErrorResponse(

        Instant timestamp,
        int status,
        String error,
        String message

) {
}