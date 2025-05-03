package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class ResponseNotFoundException extends NotFoundException {
    public ResponseNotFoundException() {
        super(ErrorMessage.RESPONSE_NOT_FOUND);
    }
}
