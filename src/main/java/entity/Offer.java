package entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
public class Offer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private LocalDateTime offerDate;

    private double suggestedPrice;

    private int executionTime;

    private LocalDateTime startTime;

    public Long getId() {
        return id;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public Order getOrder() {
        return order;
    }

    public double getSuggestedPrice() {
        return suggestedPrice;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public LocalDateTime getOfferDate() {
        return offerDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setSuggestedPrice(double suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    public void setOfferDate(LocalDateTime offerDate) {
        this.offerDate = offerDate;
    }
}
