package com.example.maktabproject1.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleType role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatusType status;

    private LocalDateTime registrationDate;

    @Lob
    @Column(name = "image_data")
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;

}
