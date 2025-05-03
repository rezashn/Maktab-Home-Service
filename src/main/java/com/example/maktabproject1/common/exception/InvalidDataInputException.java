package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class InvalidDataInputException extends BadRequestException {
    public InvalidDataInputException() {
        super(ErrorMessage.INVALID_DATA_INPUT);
    }
}
