package com.example.maktabproject1.servicemanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
public class SpecialistDto {
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    private BigDecimal rating;

    private List<Long> subServiceIds;

}