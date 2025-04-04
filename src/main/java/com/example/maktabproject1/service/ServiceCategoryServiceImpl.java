package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.ServiceCategoryDto;
import com.example.maktabproject1.entity.ServiceCategoryEntity;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.ServiceCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    public ServiceCategoryServiceImpl(ServiceCategoryRepository serviceCategoryRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    @Override
    public ServiceCategoryDto createServiceCategory(ServiceCategoryDto serviceCategoryDTO) {
        ServiceCategoryEntity serviceCategoryEntity = mapDtoToEntity(serviceCategoryDTO);
        ServiceCategoryEntity savedEntity = serviceCategoryRepository.save(serviceCategoryEntity);
        log.info("Service category created with ID: {}", savedEntity.getId());
        return mapEntityToDto(savedEntity);
    }

    @Override
    public ServiceCategoryDto getServiceCategoryById(Long id) {
        return mapEntityToDto(serviceCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Service category not found: " + id)));
    }

    @Override
    public List<ServiceCategoryDto> getAllServiceCategories() {
        return serviceCategoryRepository.findAll().stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public ServiceCategoryDto updateServiceCategory(Long id, ServiceCategoryDto serviceCategoryDTO) {
        ServiceCategoryEntity existingCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Service category not found: " + id));

        ServiceCategoryEntity updatedCategory = mapDtoToEntity(serviceCategoryDTO);
        updatedCategory.setId(id);
        ServiceCategoryEntity savedUpdatedEntity = serviceCategoryRepository.save(updatedCategory);
        log.info("Service category updated with ID: {}", savedUpdatedEntity.getId());
        return mapEntityToDto(savedUpdatedEntity);
    }

    @Override
    public void deleteServiceCategory(Long id) {
        if (!serviceCategoryRepository.existsById(id)) {
            throw new ResponseNotFoundException("Service category not found: " + id);
        }
        serviceCategoryRepository.deleteById(id);
        log.info("Service category deleted with ID: {}", id);
    }

    private ServiceCategoryEntity mapDtoToEntity(ServiceCategoryDto serviceCategoryDTO) {
        ServiceCategoryEntity serviceCategoryEntity = new ServiceCategoryEntity();
        serviceCategoryEntity.setId(serviceCategoryDTO.getId());
        serviceCategoryEntity.setName(serviceCategoryDTO.getName());
        serviceCategoryEntity.setDescription(serviceCategoryDTO.getDescription());
        return serviceCategoryEntity;
    }

    private ServiceCategoryDto mapEntityToDto(ServiceCategoryEntity serviceCategoryEntity) {
        ServiceCategoryDto dto = new ServiceCategoryDto();
        dto.setId(serviceCategoryEntity.getId());
        dto.setName(serviceCategoryEntity.getName());
        dto.setDescription(serviceCategoryEntity.getDescription());
        return dto;
    }
}