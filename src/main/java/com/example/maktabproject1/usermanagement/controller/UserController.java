package com.example.maktabproject1.usermanagement.controller;

import com.example.maktabproject1.Configuration.CustomUserDetails;
import com.example.maktabproject1.ResponseDto;
import com.example.maktabproject1.usermanagement.dto.UserDto;
import com.example.maktabproject1.usermanagement.dto.ChangePasswordDto;
import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import com.example.maktabproject1.usermanagement.entity.UserStatusType;
import com.example.maktabproject1.servicemanagement.exception.ResponseNotFoundException;
import com.example.maktabproject1.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseDto<UserDto> registerUser(@RequestBody UserDto userDto) {
        try {
            UserDto createdUser = userService.registerUser(userDto);
            return new ResponseDto<>(true, createdUser, null);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error registering user: " + e.getMessage());
        }
    }


    @GetMapping("/profile")
    public ResponseDto<UserDto> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            Long userId = userDetails.getId();

            try {
                UserDto userDto = userService.getUserById(userId);
                return new ResponseDto<>(true, userDto, null);
            } catch (ResponseNotFoundException e) {
                return new ResponseDto<>(false, null, "User not found.");
            } catch (Exception e) {
                return new ResponseDto<>(false, null, "Error fetching user profile: " + e.getMessage());
            }
        } else {
            return new ResponseDto<>(false, null, "Invalid user session.");
        }
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<UserDto> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = userService.getUserById(id);
            return new ResponseDto<>(true, userDto, null);
        } catch (ResponseNotFoundException e) {
            return new ResponseDto<>(false, null, "User not found with id: " + id);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error fetching user: " + e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserDto>> getAllUsers() {
        try {
            List<UserDto> userDtos = userService.getAllUsers();
            return new ResponseDto<>(true, userDtos, null);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error fetching users: " + e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserDto>> getUsersByStatus(@PathVariable UserStatusType status) {
        try {
            List<UserDto> usersByStatus = userService.getUsersByStatus(status);
            return new ResponseDto<>(true, usersByStatus, null);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error fetching users by status: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/image")
    public ResponseDto<Void> setUserImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (!id.equals(userId) && !isAdmin) {
            return new ResponseDto<>(false, null, "You can only change your own password.");
        }

        try {
            userService.setUserImage(id, image);
            return new ResponseDto<>(true, null, "Image uploaded successfully.");
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error uploading image: " + e.getMessage());
        }
    }


    @PutMapping("/profile")
    public ResponseDto<UserDto> updateUser(@RequestBody UserDto userDto) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userDto.getId().equals(userId) && !userDto.getRole().equals(UserRoleType.ADMIN)) {
            return new ResponseDto<>(false, null, "You can only update your own profile.");
        }
        try {
            UserDto updatedUser = userService.updateUser(userId, userDto);
            return new ResponseDto<>(true, updatedUser, null);
        } catch (ResponseNotFoundException e) {
            return new ResponseDto<>(false, null, "User not found.");
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error updating user: " + e.getMessage());
        }
    }

    @GetMapping("/last-name/{lastName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserDto>> getUserByLastName(@PathVariable String lastName) {
        try {
            List<UserDto> usersByLastName = userService.getUserByLastName(lastName);
            return new ResponseDto<>(true, usersByLastName, null);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error fetching users by last name: " + e.getMessage());
        }
    }


    @GetMapping("/first-name/{firstName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserDto>> getUserByFirstName(@PathVariable String firstName) {
        try {
            List<UserDto> usersByFirstName = userService.getUserByFirstName(firstName);
            return new ResponseDto<>(true, usersByFirstName, null);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error fetching users by first name: " + e.getMessage());
        }
    }


    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserDto>> getUsersByRole(@PathVariable UserRoleType role) {
        try {
            List<UserDto> usersByRole = userService.getUsersByRole(role);
            return new ResponseDto<>(true, usersByRole, null);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error fetching users by role: " + e.getMessage());
        }
    }


    @PutMapping("/{id}/status")
    public ResponseDto<Void> updateUserStatus(@PathVariable Long id, @RequestParam UserStatusType newStatus) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (!id.equals(userId) && !isAdmin) {
            return new ResponseDto<>(false, null, "You can only change your own password.");
        }

        try {
            userService.updateUserStatus(id, newStatus);
            return new ResponseDto<>(true, null, "User status updated successfully.");
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error updating user status: " + e.getMessage());
        }
    }


    @GetMapping("/{id}/image")
    public ResponseDto<byte[]> getUserImage(@PathVariable Long id) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (!id.equals(userId) && !isAdmin) {
            return new ResponseDto<>(false, null, "You can only change your own password.");
        }

        try {
            byte[] imageData = userService.getUserImageData(id);
            return new ResponseDto<>(true, imageData, null);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error fetching user image: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseDto<>(true, null, null);
        } catch (ResponseNotFoundException e) {
            return new ResponseDto<>(false, null, "User not found with id: " + id);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error deleting user: " + e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<UserDto> getUserByEmail(@PathVariable String email) {
        try {
            UserDto userDto = userService.getUserByEmail(email);
            return new ResponseDto<>(true, userDto, null);
        } catch (ResponseNotFoundException e) {
            return new ResponseDto<>(false, null, "User not found with email: " + email);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error fetching user by email: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/change-password")
    public ResponseDto<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDto changePasswordDto) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (!id.equals(userId) && !isAdmin) {
            return new ResponseDto<>(false, null, "You can only change your own password.");
        }

        try {
            userService.changePassword(id, changePasswordDto);
            return new ResponseDto<>(true, null, "Password changed successfully.");
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error changing password: " + e.getMessage());
        }
    }
}

