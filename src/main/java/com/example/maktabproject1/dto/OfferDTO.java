package com.example.maktabproject1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OfferDTO {
    private Long id;

    @NotNull(message = "Specialist ID is required")
    private Long specialistId;

    @NotNull(message = "Order ID is required")
    private Long orderId;

    private BigDecimal proposedPrice;

    private LocalDateTime offerDate;

    private int offerDuration;
}