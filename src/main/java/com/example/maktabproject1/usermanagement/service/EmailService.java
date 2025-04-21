package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.usermanagement.entity.UserEntity;

public interface EmailService {

    void sendVerificationEmail(UserEntity user, String token);

}
