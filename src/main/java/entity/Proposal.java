package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "proposals")
public class Proposal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private LocalDateTime proposalDate;

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

    public LocalDateTime getProposalDate() {
        return proposalDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getExecutionTime() {
        return executionTime;
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

    public void setProposalDate(LocalDateTime proposalDate) {
        this.proposalDate = proposalDate;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
