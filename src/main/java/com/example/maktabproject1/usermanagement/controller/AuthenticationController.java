package com.example.maktabproject1.usermanagement.controller;

import com.example.maktabproject1.usermanagement.service.VerificationTokenServiceImpl;
import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import com.example.maktabproject1.usermanagement.entity.UserStatusType;
import com.example.maktabproject1.usermanagement.entity.VerificationTokenEntity;
import com.example.maktabproject1.usermanagement.Repository.UserRepository;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import com.example.maktabproject1.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final VerificationTokenServiceImpl verificationTokenService;
    private final UserRepository userRepository;

    public AuthenticationController(VerificationTokenServiceImpl verificationTokenService, UserRepository userRepository) {
        this.verificationTokenService = verificationTokenService;
        this.userRepository = userRepository;
    }

    @GetMapping("/verify")
    public ResponseDto<String> verifyAccount(@RequestParam("token") String token) {
        try {
            log.info("Verifying account with token: {}", token);

            VerificationTokenEntity verificationToken = verificationTokenService.validateToken(token);
            UserEntity user = verificationToken.getUser();

            if (user == null) {
                log.error("No user found for verification token: {}", token);
                return new ResponseDto<>(false, null, "User not found for the given token.");
            }

            if (user.getRole() == UserRoleType.SPECIALIST) {
                user.setStatus(UserStatusType.PENDING);
            } else {
                user.setStatus(UserStatusType.APPROVED);
            }

            userRepository.save(user);

            log.info("User account successfully verified: {}", user.getEmail());
            return new ResponseDto<>(true, "Account successfully verified!", null);

        } catch (Exception e) {
            log.error("Error verifying account with token: {}. Error: {}", token, e.getMessage(), e);
            return new ResponseDto<>(false, null, "Verification failed: " + e.getMessage());
        }
    }
}
