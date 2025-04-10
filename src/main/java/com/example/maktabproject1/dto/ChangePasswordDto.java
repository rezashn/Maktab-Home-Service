package com.example.maktabproject1.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
public class ChangePasswordDto {

    @NotBlank(message = "Current password is required")
    private String currentPassword;

    @Size(min = 8, message = "New password must be at least 8 characters")
    @NotBlank(message = "New password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "New password must be at least 8 characters long and contain at least one uppercase letter," +
                    " one lowercase letter, and one digit"
    )
    private String newPassword;
}
