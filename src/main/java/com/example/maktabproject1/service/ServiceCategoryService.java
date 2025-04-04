package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.ServiceCategoryDto;
import com.example.maktabproject1.entity.ServiceCategoryEntity;

import java.util.List;

public interface ServiceCategoryService {
    ServiceCategoryDto createServiceCategory(ServiceCategoryDto serviceCategoryDTO);
    ServiceCategoryDto getServiceCategoryById(Long id);
    List<ServiceCategoryDto> getAllServiceCategories();
    ServiceCategoryDto updateServiceCategory(Long id, ServiceCategoryDto serviceCategoryDTO);
    void deleteServiceCategory(Long id);
}
