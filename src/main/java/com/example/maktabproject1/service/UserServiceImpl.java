package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.UserRoleEntity;
import com.example.maktabproject1.exception.DuplicateResourceException;
import com.example.maktabproject1.exception.InvalidDataInputException;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.UserRepository;
import com.example.maktabproject1.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity registerUser(UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("EMAIL ALREADY EXISTS");
        }

        if (!isValidPassword(user.getPassword())) {
            throw new InvalidDataInputException("PASSWORD DOES NOT MATCH");
        }

        user.setRegistrationDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public UserEntity changePassword(Long userId, String newPassword) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseNotFoundException("USER NOT FOUND"));

        if (!isValidPassword(newPassword)) {
            throw new InvalidDataInputException("PASSWORD SHOULD BE AT LEAST 8 CHARACTERS");
        }

        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> getUsers(UserRoleEntity role, String firstName, String lastName, String email) {

        List<UserEntity> users = userRepository.findAll();

        if (role != null) {
            users.removeIf(user -> user.getRole() != role);
        }

        if (firstName != null && !firstName.isEmpty()) {
            users.removeIf(user -> !user.getFirstName().contains(firstName));
        }

        if (lastName != null && !lastName.isEmpty()) {
            users.removeIf(user -> !user.getLastName().contains(lastName));
        }

        if (email != null && !email.isEmpty()) {
            users.removeIf(user -> !user.getEmail().contains(email));
        }

        return users;
    }

    @Override
    public UserEntity getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("USER NOT FOUND!"));
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        return hasLetter && hasDigit;
    }
}