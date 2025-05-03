package com.example.maktabproject1.common.exception;

import com.example.maktabproject1.common.ErrorMessage;

public class OfferNotFoundException extends NotFoundException {
    public OfferNotFoundException() {
        super(ErrorMessage.OFFER_NOT_FOUND.getMessage());
    }
}