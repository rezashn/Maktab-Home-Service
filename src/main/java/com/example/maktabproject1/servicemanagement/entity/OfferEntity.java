package com.example.maktabproject1.servicemanagement.entity;

import com.example.maktabproject1.usermanagement.entity.SpecialistEntity;
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

    @Column(nullable = false)
    private LocalDateTime offerDate = LocalDateTime.now();

    @Column(nullable = false)
    private BigDecimal suggestedPrice;

    @Column(nullable = false)
    private int executionTime;

    private LocalDateTime startTime;

    public OfferEntity() {}

    public OfferEntity(SpecialistEntity specialist, OrderEntity order, BigDecimal suggestedPrice,
                       int executionTime, LocalDateTime startTime) {
        this.specialist = specialist;
        this.order = order;
        this.suggestedPrice = suggestedPrice;
        this.executionTime = executionTime;
        this.startTime = startTime;
        this.offerDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public SpecialistEntity getSpecialist() {
        return specialist;
    }

    public void setSpecialist(SpecialistEntity specialist) {
        this.specialist = specialist;
    }

    public BigDecimal getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(BigDecimal suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public LocalDateTime getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(LocalDateTime offerDate) {
        this.offerDate = offerDate;
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
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OfferEntity{" +
                "id=" + id +
                ", specialist=" + specialist.getId() + // assuming SpecialistEntity has getId method
                ", order=" + order.getId() + // assuming OrderEntity has getId method
                ", offerDate=" + offerDate +
                ", suggestedPrice=" + suggestedPrice +
                ", executionTime=" + executionTime +
                ", startTime=" + startTime +
                '}';
    }
}
