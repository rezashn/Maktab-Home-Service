package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.ChangePasswordDto;
import com.example.maktabproject1.dto.UserDto;
import com.example.maktabproject1.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDTO) {
        try {
            log.info("Attempting to register user: {}", userDTO);
            UserDto registeredUser = userService.registerUser(userDTO);
            log.info("User registered successfully: {}", registeredUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        log.info("Fetching user by ID: {}", userId);
        try {
            UserDto userDto = userService.getUserById(userId);
            log.info("User found: {}", userDto);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            log.error("Error fetching user: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Fetching all users.");
        try {
            List<UserDto> userDtos = userService.getAllUsers();
            log.info("Found {} users.", userDtos.size());
            return ResponseEntity.ok(userDtos);
        } catch (Exception e) {
            log.error("Error fetching all users: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDTO) {
        try {
            log.info("Attempting to update user with ID: {}, data: {}", userId, userDTO);
            UserDto updatedUser = userService.updateUser(userId, userDTO);
            log.info("User updated successfully: {}", updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        log.info("Attempting to delete user with ID: {}", userId);
        try {
            userService.deleteUser(userId);
            log.info("User deleted successfully: {}", userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{userId}/image")
    public ResponseEntity<String> uploadUserImage(
            @PathVariable Long userId,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            log.info("Attempting to upload image for user ID: {}", userId);
            userService.setUserImage(userId, image);
            log.info("Image uploaded successfully for user ID: {}", userId);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (IOException e) {
            log.error("Error uploading image for user ID: {}", userId, e);
            return ResponseEntity.badRequest().body("Image upload failed: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("Runtime error uploading image for user ID: {}", userId, e);
            return ResponseEntity.badRequest().body("Image upload failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error uploading image for user ID: {}", userId, e);
            return ResponseEntity.internalServerError().body("Unexpected error occurred.");
        }
    }
}