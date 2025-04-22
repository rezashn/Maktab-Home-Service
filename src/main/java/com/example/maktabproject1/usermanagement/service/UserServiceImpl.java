package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.usermanagement.Repository.SpecialistRepository;
import com.example.maktabproject1.usermanagement.dto.ChangePasswordDto;
import com.example.maktabproject1.usermanagement.dto.UserDto;
import com.example.maktabproject1.usermanagement.entity.UserRoleType;
import com.example.maktabproject1.usermanagement.entity.UserStatusType;
import com.example.maktabproject1.servicemanagement.exception.DuplicateResourceException;
import com.example.maktabproject1.servicemanagement.exception.ResponseNotFoundException;
import com.example.maktabproject1.usermanagement.Repository.UserRepository;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenServiceImpl verificationTokenServiceImpl;
    private final EmailServiceImpl emailService;
    private final SpecialistRepository specialistRepository;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           VerificationTokenServiceImpl verificationTokenServiceImpl,
                           EmailServiceImpl emailService, SpecialistRepository specialistRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenServiceImpl = verificationTokenServiceImpl;
        this.emailService = emailService;
        this.specialistRepository = specialistRepository;
    }

    @Override
    public UserDto registerUser(UserDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }

        UserEntity entity = mapDtoToEntity(dto);
        entity.setRole(dto.getRole() != null ? dto.getRole() : UserRoleType.CUSTOMER);

        if (entity.getRole() == UserRoleType.SPECIALIST) {
            entity.setStatus(UserStatusType.PENDING);
        } else {
            entity.setStatus(UserStatusType.NEW);
        }

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(hashedPassword);
        entity.setRegistrationDate(LocalDateTime.now());

        UserEntity savedUser = userRepository.save(entity);
        String token = verificationTokenServiceImpl.createToken(savedUser);
        emailService.sendVerificationEmail(savedUser, token);

        log.info("User registered with ID: {}", savedUser.getId());

        return mapEntityToDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(() -> new ResponseNotFoundException("User not found: " + id));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserDto dto) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("User not found: " + id));

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());

        UserEntity updated = userRepository.save(entity);
        log.info("User updated with ID: {}", updated.getId());
        return mapEntityToDto(updated);
    }


    @Override
    public void deleteUser(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResponseNotFoundException("User not found: " + id);
        }

        UserEntity user = optionalUser.get();

        if (user.getRole() == UserRoleType.SPECIALIST) {
            specialistRepository.deleteByUserId(id);
        }

        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getUsersByRole(UserRoleType role) {
        return userRepository.findByRole(role).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUserByFirstName(String firstName) {
        return userRepository.findByFirstNameIgnoreCase(firstName).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUserByLastName(String lastName) {
        return userRepository.findByLastNameIgnoreCase(lastName).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) throw new ResponseNotFoundException("User not found: " + email);
        return mapEntityToDto(user);
    }


    @Override
    public UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("User not found: " + id));
    }

    @Override
    public void changePassword(Long userId, ChangePasswordDto changePasswordDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseNotFoundException("User not found: " + userId));

        if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid current password");
        }

        String encodedNewPassword = passwordEncoder.encode(changePasswordDto.getNewPassword());
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
        log.info("Password changed successfully for user ID: {}", userId);
    }

    private UserEntity mapDtoToEntity(UserDto dto) {
        UserEntity entity = new UserEntity();
        if (dto == null) {
            throw new IllegalArgumentException("UserDto cannot be null");
        }
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        return entity;
    }

    private UserDto mapEntityToDto(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        return dto;
    }


    @Override
    public List<UserDto> getUsersByStatus(UserStatusType status) {
        return userRepository.findByStatus(status).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public void updateUserStatus(Long userId, UserStatusType newStatus) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseNotFoundException("User not found: " + userId));
        user.setStatus(newStatus);
        userRepository.save(user);
    }

    @Override
    public void setUserImage(Long userId, MultipartFile image) throws IOException {
        try {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseNotFoundException("User not found"));

            user.setImageData(image.getBytes());
            userRepository.save(user);

            log.info("Image uploaded and user updated successfully for user ID: {}", userId);
        } catch (IOException e) {
            log.error("IOException while saving image for user ID: {}", userId, e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while saving image for user ID: {}", userId, e);
            throw new RuntimeException("Failed to save image", e);
        }
    }

    @Override
    public byte[] getUserImageData(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseNotFoundException("User not found"));

        return user.getImageData();
    }
}
