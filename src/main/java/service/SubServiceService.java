package service;

import entity.SubService;

import java.util.List;

public interface SubServiceService {
    SubService addSubService(SubService subService);

    SubService updateSubService(Long subServiceId, SubService subServiceDetails);

    void deleteSubService(Long subServiceId);

    SubService getSubServiceById(Long subServiceId);

    List<SubService> getSubServicesByCategoryId(Long categoryId);

    public List<SubService> getAllSubServicesByServiceCategoryId(Long serviceCategoryId);
}
