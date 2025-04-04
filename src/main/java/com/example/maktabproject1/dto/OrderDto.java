package com.example.maktabproject1.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class OrderDto {
    private Long id;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    private Long specialistId;

    @NotNull(message = "SubService ID is required")
    private Long subServiceId;

    private String description;

    private String address;

    private LocalDateTime orderDate;

    private BigDecimal proposedPrice;

    private String orderStatus;

    public OrderDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Long specialistId) {
        this.specialistId = specialistId;
    }

    public Long getSubServiceId() {
        return subServiceId;
    }

    public void setSubServiceId(Long subServiceId) {
        this.subServiceId = subServiceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(BigDecimal proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(id, orderDto.id) && Objects.equals(customerId, orderDto.customerId) && Objects.equals(specialistId, orderDto.specialistId) && Objects.equals(subServiceId, orderDto.subServiceId) && Objects.equals(description, orderDto.description) && Objects.equals(address, orderDto.address) && Objects.equals(orderDate, orderDto.orderDate) && Objects.equals(proposedPrice, orderDto.proposedPrice) && Objects.equals(orderStatus, orderDto.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, specialistId, subServiceId, description, address, orderDate, proposedPrice, orderStatus);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", specialistId=" + specialistId +
                ", subServiceId=" + subServiceId +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", orderDate=" + orderDate +
                ", proposedPrice=" + proposedPrice +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}