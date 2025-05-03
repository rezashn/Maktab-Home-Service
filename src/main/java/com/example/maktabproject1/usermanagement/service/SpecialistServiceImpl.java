package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.servicemanagement.dto.SpecialistDto;
import com.example.maktabproject1.usermanagement.dto.SpecialistResponseDto;
import com.example.maktabproject1.usermanagement.entity.SpecialistEntity;
import com.example.maktabproject1.servicemanagement.entity.SubServiceEntity;
import com.example.maktabproject1.common.exception.ResponseNotFoundException;
import com.example.maktabproject1.usermanagement.Repository.SpecialistRepository;
import com.example.maktabproject1.servicemanagement.repository.SubServiceRepository;
import com.example.maktabproject1.usermanagement.Repository.UserRepository;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final UserRepository userRepository;
    private final SubServiceRepository subServiceRepository;

    @Autowired
    public SpecialistServiceImpl(SpecialistRepository specialistRepository,
                                 UserRepository userRepository,
                                 SubServiceRepository subServiceRepository) {
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
        List<SpecialistEntity> specialists = specialistRepository.findAll();
        Map<Long, UserEntity> userMap = userRepository.findAllById(
                specialists.stream().map(SpecialistEntity::getId).collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(UserEntity::getId, u -> u));

        return specialists.stream()
                .map(specialist -> {
                    UserEntity user = userMap.get(specialist.getId());
                    if (user == null) {
                        throw new ResponseNotFoundException("User not found for specialist id: " + specialist.getId());
                    }
                    return mapToResponseDto(user, specialist);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecialistResponseDto> getSpecialistsBySubServiceName(String subServiceName) {
        SubServiceEntity subService = subServiceRepository.findByName(subServiceName)
                .orElseThrow(() -> new ResponseNotFoundException("Sub-service not found: " + subServiceName));
        return specialistRepository.findBySubServices(subService).stream()
                .map(specialist -> mapToResponseDto(specialist.getUser(), specialist))
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

    @Override
    public List<SpecialistResponseDto> searchSpecialistsBySubService(String subServiceName) {
        return specialistRepository.findBySubServiceNameContaining(subServiceName).stream()
                .map(specialist -> {
                    UserEntity user = userRepository.findById(specialist.getId())
                            .orElseThrow(() -> new ResponseNotFoundException("User not found for specialist id: " + specialist.getId()));
                    return mapToResponseDto(user, specialist);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecialistResponseDto> searchSpecialistsByRating(BigDecimal minRating) {
        return specialistRepository.findByRatingGreaterThanEqual(minRating).stream()
                .map(specialist -> mapToResponseDto(specialist.getUser(), specialist))
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecialistResponseDto> searchSpecialistsBySubServiceAndRating(String subServiceName, BigDecimal minRating) {
        return specialistRepository.findBySubServiceNameContainingAndRatingGreaterThanEqual(subServiceName, minRating).stream()
                .map(specialist -> mapToResponseDto(specialist.getUser(), specialist))
                .collect(Collectors.toList());
    }

    @Override
    public void save(SpecialistEntity specialistEntity) {
        specialistRepository.save(specialistEntity);
    }

    private SpecialistResponseDto mapToResponseDto(UserEntity user, SpecialistEntity specialist) {
        SpecialistResponseDto dto = new SpecialistResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRating(specialist.getRating());
        return dto;
    }

    private SpecialistDto mapEntityToDto(SpecialistEntity entity) {
        SpecialistDto dto = new SpecialistDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setRating(entity.getRating());
        if (entity.getSubServices() != null) {
            dto.setSubServiceIds(entity.getSubServices().stream()
                    .map(SubServiceEntity::getId)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
