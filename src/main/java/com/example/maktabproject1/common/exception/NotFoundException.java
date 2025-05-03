package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class NotFoundException extends BaseRuntimeException {
    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
