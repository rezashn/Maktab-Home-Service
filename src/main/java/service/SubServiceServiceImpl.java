package service;

import entity.SubService;
import exception.DuplicateResourceException;
import exception.ResponseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SubServiceRepository;

import java.util.List;

@Service
public class SubServiceServiceImpl implements SubServiceService {


    private final SubServiceRepository subServiceRepository;
    private final ServiceCategoryServiceImpl serviceCategoryServiceImpl;

    @Autowired
    public SubServiceServiceImpl(SubServiceRepository subServiceRepository, ServiceCategoryServiceImpl serviceCategoryServiceImpl) {
        this.subServiceRepository = subServiceRepository;
        this.serviceCategoryServiceImpl = serviceCategoryServiceImpl;
    }

    public SubService addSubService(SubService subService) {
        serviceCategoryServiceImpl.getServiceCategoryById(subService.getServiceCategory().getId());
        if (subServiceRepository.existsByNameAndServiceCategoryId(subService.getName(), subService.getServiceCategory().getId())) {
            throw new DuplicateResourceException("Sub service name already exists in this category");
        }
        return subServiceRepository.save(subService);
    }

    public SubService updateSubService(Long id, SubService subService) {
        SubService existingSubService = subServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("SubService not found"));
        existingSubService.setName(subService.getName());
        existingSubService.setDescription(subService.getDescription());
        existingSubService.setBasePrice(subService.getBasePrice());
        existingSubService.setServiceCategory(serviceCategoryServiceImpl.getServiceCategoryById(subService.getServiceCategory().getId()));
        return subServiceRepository.save(existingSubService);
    }

    public void deleteSubService(Long id) {
        if (!subServiceRepository.existsById(id)) {
            throw new ResponseNotFoundException("SubService not found");
        }
        subServiceRepository.deleteById(id);
    }

    public SubService getSubServiceById(Long id) {
        return subServiceRepository.findById(id).orElseThrow(() -> new ResponseNotFoundException("SubService Not found"));
    }

    public List<SubService> getAllSubServicesByServiceCategoryId(Long serviceCategoryId) {
        return subServiceRepository.findByServiceCategoryId(serviceCategoryId);
    }
}
