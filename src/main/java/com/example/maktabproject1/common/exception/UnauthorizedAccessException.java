package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class UnauthorizedAccessException extends BaseRuntimeException {
    public UnauthorizedAccessException() {
        super(ErrorMessage.UNAUTHORIZED_ACCESS);
    }
}
