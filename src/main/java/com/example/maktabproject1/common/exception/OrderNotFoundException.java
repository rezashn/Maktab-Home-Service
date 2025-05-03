package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException() {
        super(ErrorMessage.ORDER_NOT_FOUND.getMessage());
    }
}