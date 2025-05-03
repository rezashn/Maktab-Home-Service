package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.usermanagement.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender mailSender;

    @Value("${app.mail.from:default-email@example.com}")
    private String fromEmail;

    @Value("${app.verification.url:http://localhost:8080/api/auth/verify?token=}")
    private String verificationBaseUrl;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationEmail(UserEntity user, String token) {
        String subject = "Email Verification";
        String confirmationUrl = verificationBaseUrl + token;
        String message = String.format(
                "Hi %s,\n\nPlease click the link below to verify your account:\n%s\n\nThank you!",
                user.getFirstName() != null ? user.getFirstName() : "User",
                confirmationUrl
        );

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(fromEmail);
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);

        try {
            mailSender.send(email);
            logger.info("Verification email sent to {}", user.getEmail());
        } catch (MailException e) {
            logger.error("Failed to send email to {}: {}", user.getEmail(), e.getMessage());
        }
    }
}
