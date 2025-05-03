package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(ErrorMessage error) {
        super(error.toString());
    }
}
