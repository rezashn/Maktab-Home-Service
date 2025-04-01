package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.SpecialistEntity;

import java.util.List;

public interface SpecialistService {
    SpecialistEntity addSpecialist(SpecialistEntity specialistEntity);

    SpecialistEntity updateSpecialist(Long specialistId, SpecialistEntity specialistEntity);

    void deleteSpecialist(Long specialistId);

    SpecialistEntity getSpecialistById(Long specialistId);

    List<SpecialistEntity> getAllSpecialists();

    void addSubServiceToSpecialist(Long specialistId, Long subServiceId);

    void removeSubServiceFromSpecialist(Long specialistId, Long subServiceId);
}
