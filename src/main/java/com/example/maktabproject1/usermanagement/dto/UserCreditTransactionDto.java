package com.example.maktabproject1.usermanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserCreditTransactionDto {

    private Long id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    private LocalDateTime transactionDate;

    private String description;

}