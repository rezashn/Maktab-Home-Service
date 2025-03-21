package service;

import entity.ServiceCategory;
import exception.DuplicateResourceException;
import exception.ResponseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ServiceCategoryRepository;

import java.util.List;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {


    private final ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    public ServiceCategoryServiceImpl(ServiceCategoryRepository serviceCategoryRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    @Override
    public ServiceCategory addServiceCategory(ServiceCategory serviceCategory) {
        if (serviceCategoryRepository.existsByName(serviceCategory.getName())) {
            throw new DuplicateResourceException("Service category name already exists");
        }
        return serviceCategoryRepository.save(serviceCategory);
    }

    @Override
    public ServiceCategory updateServiceCategory(Long id, ServiceCategory serviceCategory) {
        ServiceCategory existingCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Service category not found"));
        existingCategory.setName(serviceCategory.getName());
        existingCategory.setDescription(serviceCategory.getDescription());
        return serviceCategoryRepository.save(existingCategory);
    }

    @Override
    public void deleteServiceCategory(Long id) {
        if (!serviceCategoryRepository.existsById(id)) {
            throw new ResponseNotFoundException("Service category not found");
        }
        serviceCategoryRepository.deleteById(id);
    }

    @Override
    public ServiceCategory getServiceCategoryById(Long id) {
        return serviceCategoryRepository.findById(id).orElseThrow(() -> new ResponseNotFoundException("Service Category Not found"));
    }

    @Override
    public List<ServiceCategory> getAllServiceCategories() {
        return serviceCategoryRepository.findAll();
    }
}
