package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sub_services")
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

    public Long getId() {
        return id;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}

