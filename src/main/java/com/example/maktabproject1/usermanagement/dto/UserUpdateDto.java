package com.example.maktabproject1.usermanagement.dto;

import com.example.maktabproject1.usermanagement.entity.UserRoleType;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserUpdateDto implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRoleType userRole;
    private Byte[] image;
    private BigDecimal credit;

    public UserUpdateDto() {}

    public UserUpdateDto(Long id, String firstName, String lastName, String email,
                         UserRoleType userRole, Byte[] image, BigDecimal credit) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRole = userRole;
        this.image = image;
        this.credit = credit;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoleType getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleType userRole) {
        this.userRole = userRole;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }
}
