package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class BadRequestException extends BaseRuntimeException {
    public BadRequestException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
