package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class InvalidOrderStatusException extends BadRequestException {
    public InvalidOrderStatusException() {
        super(ErrorMessage.INVALID_ORDER_STATUS.getMessage());
    }
}