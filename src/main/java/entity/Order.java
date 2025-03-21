package entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
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

    public Long getId() {
        return id;
    }

    public User getCustomer() {
        return customer;
    }

    public SubService getSubService() {
        return subService;
    }

    public String getDescription() {
        return description;
    }

    public double getSuggestedPrice() {
        return suggestedPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public String getAddress() {
        return address;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSuggestedPrice(double suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public void setSubService(SubService subService) {
        this.subService = subService;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setExecutionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }
}
