package entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

    @Entity
    @Table(name = "users")
    @Data
    public class User {

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
        private UserRole role;

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private UserStatus status;

        @Column(nullable = false)
        private LocalDateTime registrationDate;

        private double credit;
    }
}
