package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.UserDto;
import com.example.maktabproject1.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDto registerUser(UserDto userDTO);
    UserDto getUserById(Long userId);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long userId, UserDto userDTO);
    void deleteUser(Long userId);
    UserEntity getUserEntityById(Long userId);
    void setUserImage(Long userId, MultipartFile image) throws IOException;
}