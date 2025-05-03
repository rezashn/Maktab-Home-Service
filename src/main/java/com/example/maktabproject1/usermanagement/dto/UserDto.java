package com.example.maktabproject1.usermanagement.dto;

import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserDto implements Serializable {

    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit"
    )
    private String password;

    @NotNull(message = "Role is required")
    private UserRoleType role;

    @DecimalMin(value = "0.0", inclusive = true, message = "Credit must be non-negative")
    private BigDecimal credit;


    public UserDto() {}

    public UserDto(Long id, String firstName, String lastName, String email, String password, UserRoleType role, BigDecimal credit) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.credit = credit;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRoleType getRole() { return role; }
    public void setRole(UserRoleType role) { this.role = role; }

    public BigDecimal getCredit() { return credit; }
    public void setCredit(BigDecimal credit) { this.credit = credit; }
}
