package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class ReviewNotFoundException extends NotFoundException {
    public ReviewNotFoundException() {
        super(ErrorMessage.REVIEW_NOT_FOUND);
    }
}
