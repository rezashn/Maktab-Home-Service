package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.servicemanagement.dto.SpecialistDto;
import com.example.maktabproject1.usermanagement.dto.ChangePasswordDto;
import com.example.maktabproject1.usermanagement.dto.UserDto;
import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import com.example.maktabproject1.usermanagement.entity.UserStatusType;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
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

    UserDto getUserByEmail(String email);

    void changePassword(Long userId, ChangePasswordDto changePasswordDto);

    UserEntity getUserEntityById(Long userId);

    void setUserImage(Long userId, MultipartFile image) throws IOException;

    byte[] getUserImageData(Long userId);

    List<UserDto> getUsersByStatus(UserStatusType status);

    void updateUserStatus(Long userId, UserStatusType newStatus);

}
