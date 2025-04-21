package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.usermanagement.entity.VerificationTokenEntity;
import com.example.maktabproject1.usermanagement.Repository.TokenRepository;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {


    private final TokenRepository tokenRepository;

    public VerificationTokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String createToken(UserEntity user) {
        String token = UUID.randomUUID().toString();
        VerificationTokenEntity verificationToken = new VerificationTokenEntity();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpireDate(LocalDateTime.now().plusHours(1));
        tokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public VerificationTokenEntity validateToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(X -> X.getExpireDate().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new IllegalArgumentException("Token is invalid or expired"));
    }
}
