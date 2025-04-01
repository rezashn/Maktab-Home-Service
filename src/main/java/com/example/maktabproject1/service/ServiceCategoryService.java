package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.ServiceCategoryEntity;

import java.util.List;

public interface ServiceCategoryService {
    ServiceCategoryEntity addServiceCategory(ServiceCategoryEntity serviceCategory);

    ServiceCategoryEntity updateServiceCategory(Long serviceCategoryId, ServiceCategoryEntity serviceCategoryDetails);

    void deleteServiceCategory(Long serviceCategoryId);

    ServiceCategoryEntity getServiceCategoryById(Long serviceCategoryId);

    List<ServiceCategoryEntity> getAllServiceCategories();
}
