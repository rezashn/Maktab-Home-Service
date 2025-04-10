package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.SpecialistDto;
import com.example.maktabproject1.entity.SpecialistEntity;
import com.example.maktabproject1.entity.SubServiceEntity;
import com.example.maktabproject1.entity.UserEntity;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.SpecialistRepository;
import com.example.maktabproject1.repository.SubServiceRepository;
import com.example.maktabproject1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final UserRepository userRepository;
    private final SubServiceRepository subServiceRepository;
    private static final Logger log = LoggerFactory.getLogger(SpecialistServiceImpl.class);

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
        List<SpecialistEntity> entities = specialistRepository.findAll();
        for (SpecialistEntity entity : entities) {
            dtoList.add(mapEntityToDto(entity));
        }
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
        entity.setRating(dto.getRating());
        if (dto.getSubServiceIds() != null) {
            List<SubServiceEntity> services = new ArrayList<>();
            for (Long id : dto.getSubServiceIds()) {
                services.add(subServiceRepository.findById(id)
                        .orElseThrow(() -> new ResponseNotFoundException("SubService not found: " + id)));
            }
            entity.setSubServices(services);
        }
        return entity;
    }

    private SpecialistDto mapEntityToDto(SpecialistEntity entity) {
        SpecialistDto dto = new SpecialistDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setRating(entity.getRating());
        if (entity.getSubServices() != null) {
            List<Long> ids = new ArrayList<>();
            for (SubServiceEntity s : entity.getSubServices()) {
                ids.add(s.getId());
            }
            dto.setSubServiceIds(ids);
        }
        return dto;
    }

    @Override
    public List<SpecialistDto> searchSpecialistsBySubService(String subServiceName) {
        List<SpecialistEntity> entities = specialistRepository.findBySubServiceNameContaining(subServiceName);
        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecialistDto> searchSpecialistsByRating(BigDecimal minRating) {
        List<SpecialistEntity> entities = specialistRepository.findByRatingGreaterThanEqual(minRating);
        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecialistDto> searchSpecialistsBySubServiceAndRating(String subServiceName, BigDecimal minRating) {
        List<SpecialistEntity> entities = specialistRepository.findBySubServiceNameContainingAndRatingGreaterThanEqual(subServiceName, minRating);
        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecialistDto> searchSpecialistsBySubServiceEntity(SubServiceEntity subService) {
        List<SpecialistEntity> entities = specialistRepository.findBySubServices(subService);
        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
}