package com.example.maktabproject1.servicemanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "offers")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialist_id", nullable = false)
    private SpecialistEntity specialist;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    private LocalDateTime offerDate;

    private BigDecimal suggestedPrice;

    private int executionTime;

    private LocalDateTime startTime;

    public OfferEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SpecialistEntity getSpecialist() {
        return specialist;
    }

    public void setSpecialist(SpecialistEntity specialist) {
        this.specialist = specialist;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public LocalDateTime getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(LocalDateTime offerDate) {
        this.offerDate = offerDate;
    }

    public BigDecimal getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(BigDecimal suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferEntity that = (OfferEntity) o;
        return executionTime == that.executionTime && Objects.equals(id, that.id) && Objects.equals(specialist, that.specialist) && Objects.equals(order, that.order) && Objects.equals(offerDate, that.offerDate) && Objects.equals(suggestedPrice, that.suggestedPrice) && Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, specialist, order, offerDate, suggestedPrice, executionTime, startTime);
    }

    @Override
    public String toString() {
        return "OfferEntity{" +
                "id=" + id +
                ", specialist=" + specialist +
                ", order=" + order +
                ", offerDate=" + offerDate +
                ", suggestedPrice=" + suggestedPrice +
                ", executionTime=" + executionTime +
                ", startTime=" + startTime +
                '}';
    }
}