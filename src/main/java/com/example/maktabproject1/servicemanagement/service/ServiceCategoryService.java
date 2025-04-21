package com.example.maktabproject1.servicemanagement.service;

import com.example.maktabproject1.servicemanagement.dto.ServiceCategoryDto;

import java.util.List;

public interface ServiceCategoryService {

    ServiceCategoryDto createServiceCategory(ServiceCategoryDto serviceCategoryDto);

    ServiceCategoryDto getServiceCategoryById(Long id);

    List<ServiceCategoryDto> getAllServiceCategories();

    ServiceCategoryDto updateServiceCategory(Long id, ServiceCategoryDto serviceCategoryDto);

    void deleteServiceCategory(Long id);
}
