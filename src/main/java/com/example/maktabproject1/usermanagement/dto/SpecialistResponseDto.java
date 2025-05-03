package com.example.maktabproject1.usermanagement.dto;

import java.math.BigDecimal;
import java.util.List;

public class SpecialistResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private BigDecimal rating;

    private List<String> subServiceNames;

    // Constructor
    public SpecialistResponseDto() {}

    public SpecialistResponseDto(Long id, String firstName, String lastName, BigDecimal rating, List<String> subServiceNames) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.subServiceNames = subServiceNames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public List<String> getSubServiceNames() {
        return subServiceNames;
    }

    public void setSubServiceNames(List<String> subServiceNames) {
        this.subServiceNames = subServiceNames;
    }
}
