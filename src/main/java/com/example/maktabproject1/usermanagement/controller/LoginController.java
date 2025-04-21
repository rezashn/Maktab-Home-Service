package com.example.maktabproject1.usermanagement.controller;

import com.example.maktabproject1.usermanagement.dto.LoginDto;
import com.example.maktabproject1.ResponseDto;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import com.example.maktabproject1.usermanagement.entity.UserStatusType;
import com.example.maktabproject1.usermanagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto) {
        try {
            UserEntity user = userRepository.findByEmailIgnoreCase(loginDto.getEmail());
            if (user == null) {
                return new ResponseDto<>(false, null, "User not found");
            }
            if (user.getStatus() != UserStatusType.APPROVED) {
                return new ResponseDto<>(false, null, "Account not approved or verified yet.");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            if (authentication.isAuthenticated()) {
                return new ResponseDto<>(true, "Login successful!", null);
            } else {
                return new ResponseDto<>(false, null, "Invalid credentials");
            }
        } catch (AuthenticationException e) {
            return new ResponseDto<>(false, null, "Authentication failed: " + e.getMessage());
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "An unexpected error occurred: " + e.getMessage());
        }
    }
}
