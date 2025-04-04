package com.example.maktabproject1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SpecialistDto {
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    private BigDecimal rating;

    @Size(max = 255)
    private String imagePath;

    private List<Long> subServiceIds;
}