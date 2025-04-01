package com.example.maktabproject1.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "specialists")
@Data
public class SpecialistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String profilePicture;

    private double rating;

    @ManyToMany
    @JoinTable(name = "specialist_sub_services",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_service_id"))
    private List<SubServiceEntity> subServices;

}

