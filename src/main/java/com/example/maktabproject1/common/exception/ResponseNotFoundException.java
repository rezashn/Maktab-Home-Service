package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class ResponseNotFoundException extends RuntimeException {

    public ResponseNotFoundException(String message) {
        super(message);
    }

    public ResponseNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage.getCode() + ": " + errorMessage.getMessage());
    }
}
