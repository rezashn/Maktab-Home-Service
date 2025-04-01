package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.UserRoleEntity;
import com.example.maktabproject1.entity.UserEntity;

import java.util.List;

public interface UserService {
    public UserEntity registerUser(UserEntity user);

    public UserEntity changePassword(Long userId, String newPassword);

    public List<UserEntity> getUsers(UserRoleEntity role, String firstName, String lastName, String email);

    public UserEntity getUserById(long id);
}
