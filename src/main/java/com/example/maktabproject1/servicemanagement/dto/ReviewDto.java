package com.example.maktabproject1.servicemanagement.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ReviewDto {
    private Long id;

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private int rating;

    @Size(max = 500, message = "500 characters or less are ")
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDto reviewDto = (ReviewDto) o;
        return rating == reviewDto.rating &&
                Objects.equals(id, reviewDto.id) &&
                Objects.equals(orderId, reviewDto.orderId) &&
                Objects.equals(comment, reviewDto.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, rating, comment);
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
