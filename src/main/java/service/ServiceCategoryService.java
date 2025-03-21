package service;

import entity.ServiceCategory;

import java.util.List;

public interface ServiceCategoryService {
    ServiceCategory addServiceCategory(ServiceCategory serviceCategory);

    ServiceCategory updateServiceCategory(Long serviceCategoryId, ServiceCategory serviceCategoryDetails);

    void deleteServiceCategory(Long serviceCategoryId);

    ServiceCategory getServiceCategoryById(Long serviceCategoryId);

    List<ServiceCategory> getAllServiceCategories();
}
