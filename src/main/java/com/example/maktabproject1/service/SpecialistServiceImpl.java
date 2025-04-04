package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.SpecialistDto;
import com.example.maktabproject1.entity.SpecialistEntity;
import com.example.maktabproject1.entity.SubServiceEntity;
import com.example.maktabproject1.entity.UserEntity;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.SpecialistRepository;
import com.example.maktabproject1.repository.SubServiceRepository;
import com.example.maktabproject1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final UserRepository userRepository;
    private final SubServiceRepository subServiceRepository;

    @Autowired
    public SpecialistServiceImpl(SpecialistRepository specialistRepository, UserRepository userRepository, SubServiceRepository subServiceRepository) {
        this.specialistRepository = specialistRepository;
        this.userRepository = userRepository;
        this.subServiceRepository = subServiceRepository;
    }

    @Override
    public SpecialistDto createSpecialist(SpecialistDto dto) {
        return mapEntityToDto(specialistRepository.save(mapDtoToEntity(dto)));
    }

    @Override
    public SpecialistDto getSpecialistById(Long id) {
        return mapEntityToDto(specialistRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Specialist not found: " + id)));
    }

    @Override
    public List<SpecialistDto> getAllSpecialists() {
        List<SpecialistDto> dtoList = new ArrayList<>();
        specialistRepository.findAll().forEach(entity -> dtoList.add(mapEntityToDto(entity)));
        return dtoList;
    }

    @Override
    public SpecialistDto updateSpecialist(Long id, SpecialistDto dto) {
        SpecialistEntity entity = specialistRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Specialist not found: " + id));
        entity.setId(id);
        return mapEntityToDto(specialistRepository.save(mapDtoToEntity(dto)));
    }

    @Override
    public void deleteSpecialist(Long id) {
        if (!specialistRepository.existsById(id)) {
            throw new ResponseNotFoundException("Specialist not found: " + id);
        }
        specialistRepository.deleteById(id);
    }

    @Override
    public SpecialistEntity getSpecialistEntityById(Long specialistId) {
        return specialistRepository.findById(specialistId)
                .orElseThrow(() -> new ResponseNotFoundException("Specialist not found: " + specialistId));
    }

    private SpecialistEntity mapDtoToEntity(SpecialistDto dto) {
        SpecialistEntity entity = new SpecialistEntity();
        entity.setId(dto.getId());
        entity.setUser(userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseNotFoundException("User not found: " + dto.getUserId())));
        entity.setImagePath(dto.getImagePath());
        entity.setRating(dto.getRating());
        if (dto.getSubServiceIds() != null) {
            List<SubServiceEntity> services = new ArrayList<>();
            dto.getSubServiceIds().forEach(id -> services.add(subServiceRepository.findById(id)
                    .orElseThrow(() -> new ResponseNotFoundException("SubService not found: " + id))));
            entity.setSubServices(services);
        }
        return entity;
    }

    private SpecialistDto mapEntityToDto(SpecialistEntity entity) {
        SpecialistDto dto = new SpecialistDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setImagePath(entity.getImagePath());
        dto.setRating(entity.getRating());
        if (entity.getSubServices() != null) {
            List<Long> ids = new ArrayList<>();
            entity.getSubServices().forEach(s -> ids.add(s.getId()));
            dto.setSubServiceIds(ids);
        }
        return dto;
    }

    @Override
    public void setSpecialistImage(Long specialistId, MultipartFile image) throws IOException {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String timestamp = now.format(formatter);
            String filename = timestamp + "-" + image.getOriginalFilename();

            String uploadDirectory = "/Users/M.Shahrokhi/Documents/homeServiceImage/";

            Path directoryPath = Paths.get(uploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            String filePath = uploadDirectory + filename;
            image.transferTo(new File(filePath));

            SpecialistEntity specialist = specialistRepository.findById(specialistId)
                    .orElseThrow(() -> new ResponseNotFoundException("Specialist not found"));
            specialist.setImagePath(filename);
            specialistRepository.save(specialist);
            log.info("Image uploaded and specialist updated successfully for specialist ID: {}", specialistId);
        } catch (IOException e) {
            log.error("IOException while saving image for specialist ID: {}", specialistId, e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while saving image for specialist ID: {}", specialistId, e);
            throw new RuntimeException("Failed to save image", e);
        }
    }
}