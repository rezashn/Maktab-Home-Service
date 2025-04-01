package com.example.maktabproject1.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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

    private String description;

    private double suggestedPrice;

    private LocalDateTime orderDate;

    private LocalDateTime executionDate;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatusEntity status;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private SpecialistEntity specialist;

}
