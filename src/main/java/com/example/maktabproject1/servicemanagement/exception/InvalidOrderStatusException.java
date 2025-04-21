package com.example.maktabproject1.servicemanagement.exception;

public class InvalidOrderStatusException extends RuntimeException{
    public InvalidOrderStatusException(String message) {
        super(message);
    }
}
