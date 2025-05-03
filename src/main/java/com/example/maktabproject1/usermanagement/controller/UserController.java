package com.example.maktabproject1.usermanagement.controller;

import com.example.maktabproject1.ResponseDto;
import com.example.maktabproject1.usermanagement.dto.ChangePasswordDto;
import com.example.maktabproject1.usermanagement.dto.UserDto;
import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import com.example.maktabproject1.usermanagement.entity.UserStatusType;
import com.example.maktabproject1.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseDto<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.registerUser(userDto);
        return new ResponseDto<>(true, createdUser, "User registered successfully.");
    }

    @GetMapping("/profile")
    public ResponseDto<UserDto> getCurrentUser() {
        Long userId = getCurrentUserId();
        return new ResponseDto<>(true, userService.getUserById(userId), null);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseDto<>(true, userService.getUserById(id), null);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserDto>> getAllUsers() {
        return new ResponseDto<>(true, userService.getAllUsers(), null);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserDto>> getUsersByStatus(@PathVariable UserStatusType status) {
        return new ResponseDto<>(true, userService.getUsersByStatus(status), null);
    }

    @PutMapping("/profile")
    public ResponseDto<UserDto> updateProfile(@RequestBody UserDto dto) {
        Long currentUserId = getCurrentUserId();
        if (!dto.getId().equals(currentUserId) && !isAdmin()) {
            return new ResponseDto<>(false, null, "Access denied. You can only update your own profile.");
        }
        return new ResponseDto<>(true, userService.updateUser(dto.getId(), dto), "Profile updated successfully.");
    }

    @PutMapping("/{id}/image")
    public ResponseDto<Void> uploadImage(@PathVariable Long id, @RequestParam MultipartFile image) {
        Long currentUserId = getCurrentUserId();
        if (!id.equals(currentUserId) && !isAdmin()) {
            return new ResponseDto<>(false, null, "Access denied. You can only upload your own image.");
        }
        try {
            userService.setUserImage(id, image);
            return new ResponseDto<>(true, null, "Image uploaded successfully.");
        } catch (IOException e) {
            return new ResponseDto<>(false, null, "Failed to upload image.");
        }
    }

    @GetMapping("/{id}/image")
    public ResponseDto<byte[]> getImage(@PathVariable Long id) {
        Long currentUserId = getCurrentUserId();
        if (!id.equals(currentUserId) && !isAdmin()) {
            return new ResponseDto<>(false, null, "Access denied. You can only access your own image.");
        }
        return new ResponseDto<>(true, userService.getUserImageData(id), null);
    }

    @PutMapping("/change-password")
    public ResponseDto<Void> changePassword(@RequestBody ChangePasswordDto dto) {
        Long currentUserId = getCurrentUserId();
        userService.changePassword(currentUserId, dto);
        return new ResponseDto<>(true, null, "Password changed successfully.");
    }

    @PutMapping("/{id}/status")
    public ResponseDto<Void> updateStatus(@PathVariable Long id, @RequestParam UserStatusType status) {
        Long currentUserId = getCurrentUserId();
        if (!id.equals(currentUserId) && !isAdmin()) {
            return new ResponseDto<>(false, null, "Access denied. You can only update your own status.");
        }
        userService.updateUserStatus(id, status);
        return new ResponseDto<>(true, null, "User status updated.");
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserDto>> getUsersByRole(@PathVariable UserRoleType role) {
        return new ResponseDto<>(true, userService.getUsersByRole(role), null);
    }

    @GetMapping("/first-name/{firstName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserDto>> getUsersByFirstName(@PathVariable String firstName) {
        return new ResponseDto<>(true, userService.getUserByFirstName(firstName), null);
    }

    @GetMapping("/last-name/{lastName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserDto>> getUsersByLastName(@PathVariable String lastName) {
        return new ResponseDto<>(true, userService.getUserByLastName(lastName), null);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<UserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseDto<>(true, userService.getUserByEmail(email), null);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseDto<>(true, null, "User deleted successfully.");
    }

    private Long getCurrentUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
