package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.servicemanagement.dto.SpecialistDto;
import com.example.maktabproject1.servicemanagement.entity.SpecialistEntity;
import com.example.maktabproject1.servicemanagement.entity.SubServiceEntity;
import com.example.maktabproject1.servicemanagement.exception.ResponseNotFoundException;
import com.example.maktabproject1.usermanagement.Repository.SpecialistRepository;
import com.example.maktabproject1.servicemanagement.repository.SubServiceRepository;
import com.example.maktabproject1.usermanagement.Repository.UserRepository;
import com.example.maktabproject1.usermanagement.dto.SpecialistResponseDto;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public SpecialistResponseDto getSpecialistById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("User not found"));
        SpecialistEntity specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Specialist not found"));

        return mapToResponseDto(user, specialist);
    }


    @Override
    public List<SpecialistResponseDto> getAllSpecialists() {
        List<SpecialistEntity> specialistEntities = specialistRepository.findAll();
        List<Long> userIds = specialistEntities.stream().map(SpecialistEntity::getId).toList();
        List<UserEntity> users = userRepository.findAllById(userIds);
        Map<Long, UserEntity> userMap = users.stream().collect(Collectors.toMap(UserEntity::getId, u -> u));

        return specialistEntities.stream()
                .map(specialist -> {
                    UserEntity user = userMap.get(specialist.getId());
                    if (user == null) throw new ResponseNotFoundException("User not found for specialist id: " + specialist.getId());
                    return mapToResponseDto(user, specialist);
                })
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public SpecialistDto updateSpecialist(Long id, SpecialistDto dto) {
        SpecialistEntity entity = specialistRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Specialist not found: " + id));

        entity.setRating(dto.getRating());
        if (dto.getSubServiceIds() != null) {
            List<SubServiceEntity> services = dto.getSubServiceIds().stream()
                    .map(sid -> subServiceRepository.findById(sid)
                            .orElseThrow(() -> new ResponseNotFoundException("SubService not found: " + sid)))
                    .collect(Collectors.toList());
            entity.setSubServices(services);
        }

        return mapEntityToDto(specialistRepository.save(entity));
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

    public SpecialistResponseDto mapToResponseDto(UserEntity user, SpecialistEntity specialist) {
        SpecialistResponseDto dto = new SpecialistResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setImage(user.getImageData());
        dto.setRating(specialist.getRating());
        return dto;
    }


    public List<SpecialistResponseDto> searchSpecialistsBySubService(String subServiceName) {
        List<SpecialistEntity> specialistEntities = specialistRepository.findBySubServiceNameContaining(subServiceName);

        return specialistEntities.stream()
                .map(specialist -> {
                    UserEntity user = userRepository.findById(specialist.getId())
                            .orElseThrow(() -> new ResponseNotFoundException("User not found for specialist id: " + specialist.getId()));
                    return mapToResponseDto(user, specialist);
                })
                .collect(Collectors.toList());
    }


    public List<SpecialistResponseDto> searchSpecialistsByRating(BigDecimal minRating) {
        List<SpecialistEntity> specialists = specialistRepository.findByRatingGreaterThanEqual(minRating);
        return specialists.stream()
                .map(specialist -> mapToResponseDto(specialist.getUser(), specialist))
                .collect(Collectors.toList());

    }


    public List<SpecialistResponseDto> searchSpecialistsBySubServiceAndRating(String subServiceName, BigDecimal minRating) {
        List<SpecialistEntity> specialists = specialistRepository.findBySubServiceNameContainingAndRatingGreaterThanEqual(subServiceName, minRating);
        return specialists.stream()
                .map(specialist -> mapToResponseDto(specialist.getUser(), specialist))
                .collect(Collectors.toList());

    }

}