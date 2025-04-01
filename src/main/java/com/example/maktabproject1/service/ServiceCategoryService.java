package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.ServiceCategoryEntity;

import java.util.List;

public interface ServiceCategoryService {
    ServiceCategoryEntity addServiceCategory(ServiceCategoryEntity serviceCategoryEntity);

    ServiceCategoryEntity updateServiceCategory(Long serviceCategoryId, ServiceCategoryEntity serviceCategoryEntity);

    void deleteServiceCategory(Long serviceCategoryId);

    ServiceCategoryEntity getServiceCategoryById(Long serviceCategoryId);

    List<ServiceCategoryEntity> getAllServiceCategories();
}
