package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.SubServiceDto;
import com.example.maktabproject1.entity.ServiceCategoryEntity;
import com.example.maktabproject1.entity.SubServiceEntity;
import com.example.maktabproject1.exception.DuplicateResourceException;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.ServiceCategoryRepository;
import com.example.maktabproject1.repository.SubServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SubServiceServiceImpl implements SubServiceService {

    private final SubServiceRepository subServiceRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private static final Logger log = LoggerFactory.getLogger(SubServiceServiceImpl.class);


    @Autowired
    public SubServiceServiceImpl(SubServiceRepository subServiceRepository, ServiceCategoryRepository serviceCategoryRepository) {
        this.subServiceRepository = subServiceRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    @Override
    public SubServiceDto createSubService(SubServiceDto dto) {
        SubServiceEntity entity = mapDtoToEntity(dto);
        SubServiceEntity savedEntity = subServiceRepository.save(entity);
        log.info("SubService created with ID: {}", savedEntity.getId());
        return mapEntityToDto(savedEntity);
    }

    @Override
    public SubServiceDto getSubServiceById(Long id) {
        return mapEntityToDto(subServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("SubService not found: " + id)));
    }

    @Override
    public List<SubServiceDto> getAllSubServices() {
        List<SubServiceDto> dtoList = new ArrayList<>();
        List<SubServiceEntity> entities = subServiceRepository.findAll();
        for (SubServiceEntity entity : entities) {
            dtoList.add(mapEntityToDto(entity));
        }
        return dtoList;
    }

    @Override
    public SubServiceDto updateSubService(Long id, SubServiceDto dto) {
        SubServiceEntity entity = subServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("SubService not found: " + id));
        entity.setId(id);
        SubServiceEntity updatedEntity = subServiceRepository.save(mapDtoToEntity(dto));
        log.info("SubService updated with ID: {}", updatedEntity.getId());
        return mapEntityToDto(updatedEntity);
    }

    @Override
    public void deleteSubService(Long id) {
        if (!subServiceRepository.existsById(id)) {
            throw new ResponseNotFoundException("SubService not found: " + id);
        }
        subServiceRepository.deleteById(id);
        log.info("SubService deleted with ID: {}", id);
    }

    @Override
    public SubServiceEntity getSubServiceEntityById(Long subServiceId) {
        return subServiceRepository.findById(subServiceId)
                .orElseThrow(() -> new ResponseNotFoundException("SubService not found: " + subServiceId));
    }

    private SubServiceEntity mapDtoToEntity(SubServiceDto dto) {
        SubServiceEntity entity = new SubServiceEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        ServiceCategoryEntity serviceCategory = serviceCategoryRepository.findById(dto.getServiceCategoryId())
                .orElseThrow(() -> new ResponseNotFoundException("Service category not found: " + dto.getServiceCategoryId()));
        entity.setServiceCategory(serviceCategory);
        entity.setBasePrice(dto.getBasePrice());
        return entity;
    }

    private SubServiceDto mapEntityToDto(SubServiceEntity entity) {
        SubServiceDto dto = new SubServiceDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setServiceCategoryId(entity.getServiceCategory().getId());
        dto.setBasePrice(entity.getBasePrice());
        return dto;
    }
}