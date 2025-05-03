package com.example.maktabproject1.usermanagement.controller;

import com.example.maktabproject1.usermanagement.dto.UserDto;
import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import com.example.maktabproject1.usermanagement.entity.UserStatusType;
import com.example.maktabproject1.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/pending-specialists")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getPendingSpecialists() {
        List<UserDto> pendingSpecialists = userService.getUsersByStatus(UserStatusType.PENDING).stream()
                .filter(user -> user.getRole() == UserRoleType.SPECIALIST)
                .collect(Collectors.toList());

        if (pendingSpecialists.isEmpty()) {
            return ResponseEntity.status(404).body(pendingSpecialists);
        }

        return ResponseEntity.ok(pendingSpecialists);
    }

    @PostMapping("/approve-specialist/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> approveSpecialist(@PathVariable Long id) {
        UserDto specialist = userService.getUserById(id);

        if (specialist == null) {
            return ResponseEntity.status(404).body("Specialist not found.");
        }

        userService.updateUserStatus(id, UserStatusType.APPROVED);
        return ResponseEntity.ok("Specialist approved successfully.");
    }

    @PostMapping("/reject-specialist/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> rejectSpecialist(@PathVariable Long id) {
        UserDto specialist = userService.getUserById(id);

        if (specialist == null) {
            return ResponseEntity.status(404).body("Specialist not found.");
        }

        userService.updateUserStatus(id, UserStatusType.REJECTED);
        return ResponseEntity.ok("Specialist rejected.");
    }
}
