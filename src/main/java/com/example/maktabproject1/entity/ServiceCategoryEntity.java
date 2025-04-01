package com.example.maktabproject1.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "service_categories")
@Data
public class ServiceCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

}

