package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.ChangePasswordDto;
import com.example.maktabproject1.dto.UserDto;
import com.example.maktabproject1.entity.UserEntity;
import com.example.maktabproject1.entity.UserRoleType;
import com.example.maktabproject1.entity.UserStatusType;
import com.example.maktabproject1.exception.DuplicateResourceException;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(UserDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }

        UserEntity entity = mapDtoToEntity(dto);
        entity.setStatus(UserStatusType.NEW);
        entity.setRole(dto.getRole() != null ? dto.getRole() : UserRoleType.CUSTOMER);
        entity.setRegistrationDate(LocalDateTime.now());

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(hashedPassword);

        UserEntity savedEntity = userRepository.save(entity);

        log.info("User registered with ID: {}", savedEntity.getId());
        return mapEntityToDto(savedEntity);
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
        entity.setId(id);
        UserEntity updatedEntity = userRepository.save(mapDtoToEntity(dto));
        log.info("User updated with ID: {}", updatedEntity.getId());
        return mapEntityToDto(updatedEntity);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
        log.info("User deleted with ID: {}", id);
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
    public List<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
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
        if (entity == null) {
            throw new IllegalArgumentException("UserEntity cannot be null");
        }
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        return dto;
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
