package com.example.maktabproject1.servicemanagement.controller;

import com.example.maktabproject1.usermanagement.dto.UserDto;
import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import com.example.maktabproject1.usermanagement.entity.UserStatusType;
import com.example.maktabproject1.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/pending-specialists")
    public List<UserDto> getPendingSpecialists() {
        return userService.getUsersByStatus(UserStatusType.PENDING).stream()
                .filter(user -> user.getRole() == UserRoleType.SPECIALIST)
                .collect(Collectors.toList());
    }

    @PostMapping("/approve-specialist/{id}")
    public ResponseEntity<String> approveSpecialist(@PathVariable Long id) {
        userService.updateUserStatus(id, UserStatusType.APPROVED);
        return ResponseEntity.ok("Specialist approved successfully.");
    }

    @PostMapping("/reject-specialist/{id}")
    public ResponseEntity<String> rejectSpecialist(@PathVariable Long id) {
        userService.updateUserStatus(id, UserStatusType.REJECTED);
        return ResponseEntity.ok("Specialist rejected.");
    }
}
