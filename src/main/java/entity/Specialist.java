package entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "specialists")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Specialist {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        private String profilePicture;

        private double rating;

        @ManyToMany
        @JoinTable(name = "specialist_sub_services",
                joinColumns = @JoinColumn(name = "specialist_id"),
                inverseJoinColumns = @JoinColumn(name = "sub_service_id"))
        private List<SubService> subServices;

    }

