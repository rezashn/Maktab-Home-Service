package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class BadRequestException extends RuntimeException {
    private final String code;

    public BadRequestException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.code = errorMessage.getCode();
    }

    public String getCode() {
        return code;
    }
}
