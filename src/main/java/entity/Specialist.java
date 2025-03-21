package entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "specialists")
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

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public double getRating() {
        return rating;
    }

    public List<SubService> getSubServices() {
        return subServices;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSubServices(List<SubService> subServices) {
        this.subServices = subServices;
    }
}

