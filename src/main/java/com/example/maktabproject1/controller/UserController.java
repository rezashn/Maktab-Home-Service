package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.ResponseDto;
import com.example.maktabproject1.dto.UserDto;
import com.example.maktabproject1.dto.ChangePasswordDto;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseDto<UserDto> registerUser(@RequestBody UserDto userDto) {
        try {
            UserDto createdUser = userService.registerUser(userDto);
            return new ResponseDto<>(true, createdUser, null);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error registering user: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
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
    public ResponseDto<List<UserDto>> getAllUsers() {
        try {
            List<UserDto> userDtos = userService.getAllUsers();
            return new ResponseDto<>(true, userDtos, null);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error fetching users: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseDto<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return new ResponseDto<>(true, updatedUser, null);
        } catch (ResponseNotFoundException e) {
            return new ResponseDto<>(false, null, "User not found with id: " + id);
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error updating user: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
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

    @PutMapping("/{id}/change-password")
    public ResponseDto<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDto changePasswordDto) {
        try {
            userService.changePassword(id, changePasswordDto);
            return new ResponseDto<>(true, null, "Password changed successfully.");
        } catch (Exception e) {
            return new ResponseDto<>(false, null, "Error changing password: " + e.getMessage());
        }
    }
}

