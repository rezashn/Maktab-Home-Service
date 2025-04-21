package com.example.maktabproject1.servicemanagement.entity;

import com.example.maktabproject1.usermanagement.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
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

    @OneToMany(mappedBy = "order")
    private List<OfferEntity> offers;

}