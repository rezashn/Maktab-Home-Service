package com.example.maktabproject1.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "specialists")
public class SpecialistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "image_path", length = 255)
    private String imagePath;

    private BigDecimal rating;

    @ManyToMany
    @JoinTable(name = "specialist_sub_services",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_service_id"))
    private List<SubServiceEntity> subServices;

    public SpecialistEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public List<SubServiceEntity> getSubServices() {
        return subServices;
    }

    public void setSubServices(List<SubServiceEntity> subServices) {
        this.subServices = subServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialistEntity that = (SpecialistEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(imagePath, that.imagePath) && Objects.equals(rating, that.rating) && Objects.equals(subServices, that.subServices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, imagePath, rating, subServices);
    }

    @Override
    public String toString() {
        return "SpecialistEntity{" +
                "id=" + id +
                ", user=" + user +
                ", imagePath='" + imagePath + '\'' +
                ", rating=" + rating +
                ", subServices=" + subServices +
                '}';
    }
}