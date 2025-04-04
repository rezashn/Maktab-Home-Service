package com.example.maktabproject1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Specialist ID is required")
    private Long specialistId;

    @NotNull(message = "SubService ID is required")
    private Long subServiceId;

    private String description;

    private String address;

    private LocalDateTime orderDate;

    private BigDecimal proposedPrice;

    private String orderStatus;
}