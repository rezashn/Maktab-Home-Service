package com.example.maktabproject1.common;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    USER_NOT_FOUND("M40401", "User not found"),
    EMAIL_ALREADY_EXISTS("M40901", "Email already exists"),
    INVALID_CURRENT_PASSWORD("M40001", "Invalid current password"),
    USER_DTO_NULL("M40002", "UserDto cannot be null"),
    IMAGE_IS_EMPTY("M40003", "Image must not be empty"),

    SPECIALIST_NOT_FOUND("M40402", "Specialist not found"),
    REVIEW_NOT_FOUND("M40403", "Review not found"),
    RESPONSE_NOT_FOUND("M40404", "Response not found"),
    ORDER_NOT_FOUND("M40405", "Order not found"),
    OFFER_NOT_FOUND("M40406", "Offer not found"),

    INVALID_ORDER_STATUS("M40004", "Invalid order status"),
    INVALID_DATA_INPUT("M40005", "Invalid input data"),
    BAD_REQUEST("M40006", "Bad request"),
    NOT_FOUND("M40407", "Resource not found"),
    DUPLICATE_RESOURCE("M40902", "Resource already exists"),
    CONFLICT("M40903", "Conflict occurred"),
    INSUFFICIENT_CREDIT("M40007", "Insufficient credit for withdrawal"),
    UNAUTHORIZED_ACCESS("M40101", "Unauthorized access");

    private final String code;
    private final String message;

    ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}
