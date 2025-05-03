package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.common.ErrorMessage;
import com.example.maktabproject1.common.exception.BadRequestException;
import com.example.maktabproject1.common.exception.DuplicateResourceException;
import com.example.maktabproject1.common.exception.ResponseNotFoundException;
import com.example.maktabproject1.usermanagement.Repository.SpecialistRepository;
import com.example.maktabproject1.usermanagement.Repository.UserRepository;
import com.example.maktabproject1.usermanagement.dto.ChangePasswordDto;
import com.example.maktabproject1.usermanagement.dto.UserDto;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import com.example.maktabproject1.usermanagement.entity.UserStatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;


    public UserServiceImpl(UserRepository userRepository,
                           SpecialistRepository specialistRepository,
                           PasswordEncoder passwordEncoder,
                           VerificationTokenService verificationTokenService,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.specialistRepository = specialistRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
    }

    @Override
    public UserDto registerUser(UserDto dto) {
        if (dto == null) {
            throw new BadRequestException(ErrorMessage.INVALID_DATA_INPUT);
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException(ErrorMessage.DUPLICATE_RESOURCE);
        }

        UserEntity user = new UserEntity();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() != null ? dto.getRole() : UserRoleType.CUSTOMER);
        user.setStatus(user.getRole() == UserRoleType.SPECIALIST ? UserStatusType.PENDING : UserStatusType.NEW);

        UserEntity savedUser = userRepository.save(user);

        String token = verificationTokenService.createToken(savedUser);
        emailService.sendVerificationEmail(savedUser, token);

        return mapToDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException(ErrorMessage.NOT_FOUND));
        return mapToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserDto dto) {
        if (dto == null) {
            throw new BadRequestException(ErrorMessage.INVALID_DATA_INPUT);
        }

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException(ErrorMessage.NOT_FOUND));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        return mapToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException(ErrorMessage.NOT_FOUND));

        if (user.getRole() == UserRoleType.SPECIALIST) {
            specialistRepository.deleteByUserId(id);
        }

        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getUsersByRole(UserRoleType role) {
        return userRepository.findByRole(role).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUserByFirstName(String firstName) {
        return userRepository.findByFirstNameIgnoreCase(firstName).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUserByLastName(String lastName) {
        return userRepository.findByLastNameIgnoreCase(lastName).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new ResponseNotFoundException(ErrorMessage.NOT_FOUND);
        }
        return mapToDto(user);
    }

    @Override
    public UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException(ErrorMessage.NOT_FOUND));
    }

    @Override
    public void changePassword(Long userId, ChangePasswordDto dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseNotFoundException(ErrorMessage.NOT_FOUND));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessage.INVALID_DATA_INPUT);
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUserStatus(Long userId, UserStatusType newStatus) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseNotFoundException(ErrorMessage.NOT_FOUND));

        user.setStatus(newStatus);
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getUsersByStatus(UserStatusType status) {
        return userRepository.findByStatus(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void setUserImage(Long userId, MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            throw new BadRequestException(ErrorMessage.INVALID_DATA_INPUT);
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseNotFoundException(ErrorMessage.NOT_FOUND));

        user.setImageData(image.getBytes());
        userRepository.save(user);
    }

    @Override
    public byte[] getUserImageData(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseNotFoundException(ErrorMessage.NOT_FOUND));
        return user.getImageData();
    }

    // === Helper mapping methods ===

    private UserDto mapToDto(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setCredit(null); // Set if applicable
        return dto;
    }
}
