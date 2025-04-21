package com.example.maktabproject1.servicemanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class OfferDTO {
    private Long id;

    @NotNull(message = "Specialist ID is required")
    private Long specialistId;

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @PositiveOrZero(message = "Price cannot be negative")
    private BigDecimal proposedPrice;

    private LocalDateTime offerDate;

    private int offerDuration;

    public OfferDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getOfferDuration() {
        return offerDuration;
    }

    public void setOfferDuration(int offerDuration) {
        this.offerDuration = offerDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferDTO offerDTO = (OfferDTO) o;
        return offerDuration == offerDTO.offerDuration && Objects.equals(id, offerDTO.id) && Objects.equals(specialistId, offerDTO.specialistId) && Objects.equals(orderId, offerDTO.orderId) && Objects.equals(proposedPrice, offerDTO.proposedPrice) && Objects.equals(offerDate, offerDTO.offerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, specialistId, orderId, proposedPrice, offerDate, offerDuration);
    }

    @Override
    public String toString() {
        return "OfferDTO{" +
                "id=" + id +
                ", specialistId=" + specialistId +
                ", orderId=" + orderId +
                ", proposedPrice=" + proposedPrice +
                ", offerDate=" + offerDate +
                ", offerDuration=" + offerDuration +
                '}';
    }
}