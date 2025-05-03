package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.common.ErrorMessage;
import com.example.maktabproject1.common.exception.BadRequestException;
import com.example.maktabproject1.common.exception.UnauthorizedAccessException;
import com.example.maktabproject1.usermanagement.entity.VerificationTokenEntity;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import com.example.maktabproject1.usermanagement.Repository.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final TokenRepository tokenRepository;

    @Value("${token.expiry.hours:1}")
    private int tokenExpiryHours;

    public VerificationTokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String createToken(UserEntity user) {
        if (user == null) {
            throw new BadRequestException(ErrorMessage.INVALID_DATA_INPUT);
        }

        String token = UUID.randomUUID().toString();

        VerificationTokenEntity verificationToken = new VerificationTokenEntity();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpireDate(LocalDateTime.now().plusHours(tokenExpiryHours));
        verificationToken.setUsed(false);

        tokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public VerificationTokenEntity validateToken(String token) {
        if (token == null || token.isBlank()) {
            throw new BadRequestException(ErrorMessage.BAD_REQUEST);
        }

        VerificationTokenEntity tokenEntity = tokenRepository.findByToken(token)
                .filter(t -> t.getExpireDate().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new UnauthorizedAccessException(ErrorMessage.UNAUTHORIZED_ACCESS));

        if (tokenEntity.isUsed()) {
            throw new UnauthorizedAccessException(ErrorMessage.UNAUTHORIZED_ACCESS);
        }

        tokenEntity.setUsed(true);
        tokenRepository.save(tokenEntity);

        return tokenEntity;
    }
}
