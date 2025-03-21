package entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "sub_service_id", nullable = false)
    private SubService subService;

    private String description;

    private double suggestedPrice;

    private LocalDateTime orderDate;

    private LocalDateTime executionDate;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;
}
