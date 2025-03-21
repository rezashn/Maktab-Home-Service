package entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private int rating;

    private String comment;

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
