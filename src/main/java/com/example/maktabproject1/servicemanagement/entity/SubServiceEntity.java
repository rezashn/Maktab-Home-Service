package com.example.maktabproject1.servicemanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sub_services")
public class SubServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal basePrice;

    @ManyToOne
    @JoinColumn(name = "service_category_id", nullable = false)
    private ServiceCategoryEntity serviceCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceCategoryEntity getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategoryEntity serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
