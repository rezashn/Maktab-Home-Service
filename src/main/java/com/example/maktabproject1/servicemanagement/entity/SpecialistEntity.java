package com.example.maktabproject1.servicemanagement.entity;

import com.example.maktabproject1.usermanagement.entity.UserEntity;
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

    private BigDecimal rating;

    @ManyToMany
    @JoinTable(name = "specialist_sub_services",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_service_id"))
    private List<SubServiceEntity> subServices;

    @OneToMany(mappedBy = "specialist")
    private List<OfferEntity> offers;

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

    public List<OfferEntity> getOffers() {
        return offers;
    }
    public void setOffers(List<OfferEntity> offers) {
        this.offers = offers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialistEntity that = (SpecialistEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user)&& Objects.equals(rating, that.rating) && Objects.equals(subServices, that.subServices) && Objects.equals(offers, that.offers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user,  rating, subServices, offers);
    }
}