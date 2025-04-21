package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.usermanagement.entity.UserEntity;
import com.example.maktabproject1.usermanagement.entity.VerificationTokenEntity;

public interface VerificationTokenService {

    String createToken(UserEntity user);

    VerificationTokenEntity validateToken(String token);
}
