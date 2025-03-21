package entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sub_services")
@Data
public class SubService {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private double basePrice;

    @ManyToOne
    @JoinColumn(name = "service_category_id", nullable = false)
    private ServiceCategory serviceCategory;
}
}
