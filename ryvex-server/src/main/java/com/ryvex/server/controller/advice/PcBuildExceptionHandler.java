package com.ryvex.server.controller.advice;

import com.ryvex.server.controller.HardwareController;
import com.ryvex.server.controller.PcBuildController;
import com.ryvex.server.dto.pcbuild.ApiErrorResponse;
import com.ryvex.server.service.pcbuild.HardwareComponentNotFoundException;
import com.ryvex.server.service.pcbuild.PcBuildNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice(
        assignableTypes = {
                PcBuildController.class,
                HardwareController.class
        }
)
public class PcBuildExceptionHandler {

    @ExceptionHandler({
            PcBuildNotFoundException.class,
            HardwareComponentNotFoundException.class
    })
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            RuntimeException exception
    ) {

        return createResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    @ExceptionHandler(
            IllegalArgumentException.class
    )
    public ResponseEntity<ApiErrorResponse> handleBadRequest(
            IllegalArgumentException exception
    ) {

        return createResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException exception
    ) {

        String message =
                exception
                        .getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .findFirst()
                        .map(
                                error ->
                                        error.getDefaultMessage()
                        )
                        .orElse(
                                "Invalid request."
                        );

        return createResponse(
                HttpStatus.BAD_REQUEST,
                message
        );
    }

    @ExceptionHandler(
            MethodArgumentTypeMismatchException.class
    )
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception
    ) {

        return createResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid value for "
                        + exception.getName()
                        + "."
        );
    }

    @ExceptionHandler(
            ObjectOptimisticLockingFailureException.class
    )
    public ResponseEntity<ApiErrorResponse> handleConflict(
            ObjectOptimisticLockingFailureException exception
    ) {

        return createResponse(
                HttpStatus.CONFLICT,
                "This PC build was changed by another request. Reload the build and try again."
        );
    }

    private ResponseEntity<ApiErrorResponse> createResponse(
            HttpStatus status,
            String message
    ) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        message
                );

        return ResponseEntity
                .status(
                        status
                )
                .body(
                        response
                );
    }
}