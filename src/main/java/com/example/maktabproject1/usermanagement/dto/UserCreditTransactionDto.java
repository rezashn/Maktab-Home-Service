package com.example.maktabproject1.usermanagement.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserCreditTransactionDto implements Serializable {

    private Long id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    private LocalDateTime transactionDate;

    private String description;

    public UserCreditTransactionDto() {}

    public UserCreditTransactionDto(Long id, Long userId, BigDecimal amount, LocalDateTime transactionDate, String description) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
