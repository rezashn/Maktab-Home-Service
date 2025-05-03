package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class SpecialistNotFoundException extends NotFoundException {
    public SpecialistNotFoundException() {
        super(ErrorMessage.SPECIALIST_NOT_FOUND);
    }
}
