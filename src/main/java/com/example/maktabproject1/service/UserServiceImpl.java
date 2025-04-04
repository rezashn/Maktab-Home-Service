package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.UserDto;
import com.example.maktabproject1.entity.UserEntity;
import com.example.maktabproject1.entity.UserStatusEntity;
import com.example.maktabproject1.exception.DuplicateResourceException;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto registerUser(UserDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }
        UserEntity entity = mapDtoToEntity(dto);
        entity.setStatus(UserStatusEntity.NEW);
        entity.setRegistrationDate(LocalDateTime.now());
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
    @Transactional
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
    public UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("User not found: " + id));
    }

    private UserEntity mapDtoToEntity(UserDto dto) {
        UserEntity entity = new UserEntity();
        if(dto == null){
            throw new IllegalArgumentException("UserDto cannot be null");
        }
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setCredit(dto.getCredit());
        return entity;
    }

    private UserDto mapEntityToDto(UserEntity entity) {
        UserDto dto = new UserDto();
        if(entity == null){
            throw new IllegalArgumentException("UserEntity cannot be null");
        }
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setCredit(entity.getCredit());
        return dto;
    }

    @Override
    @Transactional
    public void setUserImage(Long userId, MultipartFile image) throws IOException {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String timestamp = now.format(formatter);
            String filename = timestamp + "-" + image.getOriginalFilename();

            String uploadDirectory = "/Users/M.shahrokhi/Documents/homeserviceimage/";

            Path directoryPath = Paths.get(uploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            String filePath = uploadDirectory + filename;
            image.transferTo(new File(filePath));

            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseNotFoundException("User not found"));
            user.setImagePath(filename);
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
}