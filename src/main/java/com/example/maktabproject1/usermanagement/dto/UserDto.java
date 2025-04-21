package com.example.maktabproject1.usermanagement.dto;

import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class UserDto {

    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one uppercase letter," +
                    " one lowercase letter, and one digit"
    )

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private UserRoleType role;

    private BigDecimal credit;

}