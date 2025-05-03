package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            NotFoundException.class,
            BadRequestException.class,
            ConflictException.class,
            InvalidDataInputException.class,
            InvalidOrderStatusException.class,
            DuplicateResourceException.class
    })
    public ResponseEntity<Object> handleCustomExceptions(RuntimeException ex) {
        HttpStatus status = resolveHttpStatus(ex);
        return buildResponse(ex.getMessage(), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOther(Exception ex) {
        return buildResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpStatus resolveHttpStatus(Exception ex) {
        if (ex instanceof NotFoundException) return HttpStatus.NOT_FOUND;
        if (ex instanceof BadRequestException || ex instanceof InvalidDataInputException || ex instanceof InvalidOrderStatusException)
            return HttpStatus.BAD_REQUEST;
        if (ex instanceof ConflictException || ex instanceof DuplicateResourceException)
            return HttpStatus.CONFLICT;
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private ResponseEntity<Object> buildResponse(String message, HttpStatus status) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);

        for (ErrorMessage errorMessage : ErrorMessage.values()) {
            if (errorMessage.getMessage().equals(message)) {
                error.put("errorCode", errorMessage.getCode());
                break;
            }
        }

        return new ResponseEntity<>(error, status);
    }
}
