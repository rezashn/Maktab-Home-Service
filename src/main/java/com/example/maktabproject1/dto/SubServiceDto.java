package com.example.maktabproject1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubServiceDto {
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Service category ID is required")
    private Long serviceCategoryId;

    private BigDecimal basePrice;
}