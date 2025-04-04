package com.example.maktabproject1.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class SubServiceDto {
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Service category ID is required")
    private Long serviceCategoryId;

    private BigDecimal basePrice;

    public SubServiceDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(Long serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubServiceDto that = (SubServiceDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(serviceCategoryId, that.serviceCategoryId) && Objects.equals(basePrice, that.basePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, serviceCategoryId, basePrice);
    }

    @Override
    public String toString() {
        return "SubServiceDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", serviceCategoryId=" + serviceCategoryId +
                ", basePrice=" + basePrice +
                '}';
    }
}