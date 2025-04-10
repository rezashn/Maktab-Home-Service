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

    public List<UserDto> getUsersByRole(UserRoleType role);

    public List<UserDto> getUserByFirstName(String firstName);


    public List<UserDto> getUserByLastName(String lastName);

    public List<UserDto> getUserByEmail(String email);

    public void changePassword(Long userId, ChangePasswordDto changePasswordDto);

    UserEntity getUserEntityById(Long userId);

    void setUserImage(Long userId, MultipartFile image) throws IOException;
}