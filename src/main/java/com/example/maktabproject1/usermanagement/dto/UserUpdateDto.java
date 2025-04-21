package com.example.maktabproject1.usermanagement.dto;

import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserUpdateDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private UserRoleType userRole;

    private Byte[] image;

    private BigDecimal credit;

}
