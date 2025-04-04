package com.example.maktabproject1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceCategoryDto {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;
}