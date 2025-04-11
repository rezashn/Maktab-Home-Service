package com.example.maktabproject1.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
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

    private String description;

    private BigDecimal suggestedPrice;

    private LocalDateTime orderDate;

    private LocalDateTime executionDate;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatusType status;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private SpecialistEntity specialist;

    @OneToOne
    @JoinColumn(name = "accepted_offer_id")
    private OfferEntity acceptedOffer;

    @OneToMany(mappedBy = "order")
    private List<OfferEntity> offers;

    public OrderEntity() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatusType getStatus() {
        return status;
    }

    public void setStatus(OrderStatusType status) {
        this.status = status;
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
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && Objects.equals(subService, that.subService) && Objects.equals(description, that.description) && Objects.equals(suggestedPrice, that.suggestedPrice) && Objects.equals(orderDate, that.orderDate) && Objects.equals(executionDate, that.executionDate) && Objects.equals(address, that.address) && status == that.status && Objects.equals(specialist, that.specialist) && Objects.equals(acceptedOffer, that.acceptedOffer) && Objects.equals(offers, that.offers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, subService, description, suggestedPrice, orderDate, executionDate, address, status, specialist, acceptedOffer, offers);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", customer=" + customer +
                ", subService=" + subService +
                ", description='" + description + '\'' +
                ", suggestedPrice=" + suggestedPrice +
                ", orderDate=" + orderDate +
                ", executionDate=" + executionDate +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", specialist=" + specialist +
                ", acceptedOffer=" + acceptedOffer +
                ", offers=" + offers +
                '}';
    }
}