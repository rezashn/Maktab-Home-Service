package com.example.maktabproject1.servicemanagement.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class SpecialistDto {

    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    private BigDecimal rating;

    private List<Long> subServiceIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public List<Long> getSubServiceIds() {
        return subServiceIds;
    }

    public void setSubServiceIds(List<Long> subServiceIds) {
        this.subServiceIds = subServiceIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialistDto that = (SpecialistDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(rating, that.rating) &&
                Objects.equals(subServiceIds, that.subServiceIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, rating, subServiceIds);
    }

    @Override
    public String toString() {
        return "SpecialistDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", rating=" + rating +
                ", subServiceIds=" + subServiceIds +
                '}';
    }
}
