package com.example.maktabproject1.usermanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpecialistResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private byte[] image;

    private BigDecimal rating;
}
