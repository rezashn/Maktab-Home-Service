package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public abstract class BaseRuntimeException extends RuntimeException {
    private final String code;

    public BaseRuntimeException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.code = errorMessage.getCode();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + getMessage();
    }
}
