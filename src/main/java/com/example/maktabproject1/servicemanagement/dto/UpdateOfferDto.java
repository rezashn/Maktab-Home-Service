package com.example.maktabproject1.servicemanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdateOfferDto {

    private Long specialistId;

    private Long orderId;

    @PositiveOrZero(message = "Price cannot be negative")
    private BigDecimal proposedPrice;

    private LocalDateTime offerDate;

    private Integer offerDuration;

}