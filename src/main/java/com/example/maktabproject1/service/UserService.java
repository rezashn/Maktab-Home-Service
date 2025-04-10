package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.ChangePasswordDto;
import com.example.maktabproject1.dto.UserDto;
import com.example.maktabproject1.entity.UserEntity;
import com.example.maktabproject1.entity.UserRoleType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto userDTO);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long userId, UserDto userDTO);

    void deleteUser(Long userId);

    List<UserDto> getUsersByRole(UserRoleType role);

    List<UserDto> getUserByFirstName(String firstName);

    List<UserDto> getUserByLastName(String lastName);

    List<UserDto> getUserByEmail(String email);

    void changePassword(Long userId, ChangePasswordDto changePasswordDto);

    UserEntity getUserEntityById(Long userId);

    void setUserImage(Long userId, MultipartFile image) throws IOException;

    byte[] getUserImageData(Long userId);
}