package com.example.maktabproject1.servicemanagement.entity;

import com.example.maktabproject1.usermanagement.entity.SpecialistEntity;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private UserEntity customer;

    @ManyToOne
    @JoinColumn(name = "sub_service_id", nullable = false)
    private SubServiceEntity subService;

    @Enumerated(EnumType.STRING)
    private OrderStatusType status;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private boolean isFinished;

    private Duration proposedDuration;

    private String description;

    private BigDecimal suggestedPrice;

    private LocalDateTime orderDate;

    private LocalDateTime executionDate;

    private LocalDateTime paymentRequestTime;

    private String address;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private SpecialistEntity specialist;

    @OneToOne
    @JoinColumn(name = "accepted_offer_id")
    private OfferEntity acceptedOffer;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OfferEntity> offers;

    public OrderEntity() {}

    public OrderEntity(UserEntity customer, SubServiceEntity subService, OrderStatusType status,
                       BigDecimal suggestedPrice, LocalDateTime orderDate, String address) {
        this.customer = customer;
        this.subService = subService;
        this.status = status;
        this.suggestedPrice = suggestedPrice;
        this.orderDate = orderDate;
        this.address = address;
        this.isFinished = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getCustomer() {
        return customer;
    }

    public void setCustomer(UserEntity customer) {
        this.customer = customer;
    }

    public SubServiceEntity getSubService() {
        return subService;
    }

    public void setSubService(SubServiceEntity subService) {
        this.subService = subService;
    }

    public OrderStatusType getStatus() {
        return status;
    }

    public void setStatus(OrderStatusType status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Duration getProposedDuration() {
        return proposedDuration;
    }

    public void setProposedDuration(Duration proposedDuration) {
        this.proposedDuration = proposedDuration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(BigDecimal suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
    }

    public LocalDateTime getPaymentRequestTime() {
        return paymentRequestTime;
    }

    public void setPaymentRequestTime(LocalDateTime paymentRequestTime) {
        this.paymentRequestTime = paymentRequestTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SpecialistEntity getSpecialist() {
        return specialist;
    }

    public void setSpecialist(SpecialistEntity specialist) {
        this.specialist = specialist;
    }

    public OfferEntity getAcceptedOffer() {
        return acceptedOffer;
    }

    public void setAcceptedOffer(OfferEntity acceptedOffer) {
        this.acceptedOffer = acceptedOffer;
    }

    public List<OfferEntity> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferEntity> offers) {
        this.offers = offers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", customer=" + customer.getId() +
                ", subService=" + subService.getId() +
                ", status=" + status +
                ", orderDate=" + orderDate +
                ", address='" + address + '\'' +
                '}';
    }
}
