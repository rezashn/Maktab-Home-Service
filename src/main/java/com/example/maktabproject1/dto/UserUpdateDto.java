package com.example.maktabproject1.dto;

import com.example.maktabproject1.entity.UserRoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.usertype.UserType;

import java.math.BigDecimal;

@Data
public class UserUpdateDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private UserRoleType userRole;

    private String imagePath;

    private BigDecimal credit;

}
