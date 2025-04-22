package com.example.maktabproject1.servicemanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UpdateOfferDto {

    private Long specialistId;

    private Long orderId;

    @PositiveOrZero(message = "Price cannot be negative")
    private BigDecimal proposedPrice;

    private LocalDateTime offerDate;

    private Integer offerDuration;

    public Long getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Long specialistId) {
        this.specialistId = specialistId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public LocalDateTime getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(LocalDateTime offerDate) {
        this.offerDate = offerDate;
    }

    public Integer getOfferDuration() {
        return offerDuration;
    }

    public void setOfferDuration(Integer offerDuration) {
        this.offerDuration = offerDuration;
    }

    @Override
    public String toString() {
        return "UpdateOfferDto{" +
                "specialistId=" + specialistId +
                ", orderId=" + orderId +
                ", proposedPrice=" + proposedPrice +
                ", offerDate=" + offerDate +
                ", offerDuration=" + offerDuration +
                '}';
    }
}
