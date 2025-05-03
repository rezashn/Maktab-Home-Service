package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class UnauthorizedAccessException extends RuntimeException {
    private final String code;

    public UnauthorizedAccessException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.code = errorMessage.getCode();
    }

    public String getCode() {
        return code;
    }
}
