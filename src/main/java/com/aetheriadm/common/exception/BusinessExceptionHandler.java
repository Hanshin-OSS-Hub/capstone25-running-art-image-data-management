package com.aetheriadm.common.exception;

import com.aetheriadm.common.exception.dto.ErrorMessage;
import com.aetheriadm.common.exception.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException e) {
        var errorMessage = e.getErrorMessage();

        log.error("[ERROR] BusinessException -> {}", errorMessage.getMessage());

        return ErrorResponseDto.of(errorMessage, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String detailedErrorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("[%s]: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        String globalErrorMessage = e.getBindingResult().getGlobalErrors().stream()
                .map(error -> String.format("[%s]: %s", error.getObjectName(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        String combinedMessage = Stream.of(detailedErrorMessage, globalErrorMessage)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining("; "));

        String finalMessage = combinedMessage.isEmpty()
                ? ErrorMessage.INVALID_REQUEST_PARAMETER.getMessage()
                : combinedMessage;

        log.error("[WARN] MethodArgumentNotValidException -> {}", finalMessage);

        return ErrorResponseDto.of(ErrorMessage.INVALID_REQUEST_PARAMETER, finalMessage);
    }
}