package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.SpecialistEntity;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.SpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final SubServiceServiceImpl subServiceServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public SpecialistServiceImpl(SpecialistRepository specialistRepository, SubServiceServiceImpl subServiceServiceImpl, UserServiceImpl userServiceImpl) {
        this.specialistRepository = specialistRepository;
        this.subServiceServiceImpl = subServiceServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public SpecialistEntity addSpecialist(SpecialistEntity specialist) {
        userServiceImpl.getUserById(specialist.getUser().getId());
        return specialistRepository.save(specialist);
    }

    @Override
    public SpecialistEntity updateSpecialist(Long specialistId, SpecialistEntity specialistDetails) {
        SpecialistEntity existingSpecialist = getSpecialistById(specialistId);
        existingSpecialist.setUser(userServiceImpl.getUserById(specialistDetails.getUser().getId()));
        existingSpecialist.setProfilePicture(specialistDetails.getProfilePicture());
        existingSpecialist.setRating(specialistDetails.getRating());
        existingSpecialist.setSubServices(specialistDetails.getSubServices());
        return specialistRepository.save(existingSpecialist);
    }

    @Override
    public void deleteSpecialist(Long specialistId) {
        if (!specialistRepository.existsById(specialistId)) {
            throw new ResponseNotFoundException("specialist not found");
        }
        specialistRepository.deleteById(specialistId);
    }

    @Override
    public SpecialistEntity getSpecialistById(Long specialistId) {
        return specialistRepository.findById(specialistId).orElseThrow(() -> new ResponseNotFoundException("specialist not found"));
    }

    @Override
    public List<SpecialistEntity> getAllSpecialists() {
        return specialistRepository.findAll();
    }

    @Override
    public void addSubServiceToSpecialist(Long specialistId, Long subServiceId) {
        SpecialistEntity specialist = getSpecialistById(specialistId);
        specialist.getSubServices().add(subServiceServiceImpl.getSubServiceById(subServiceId));
        specialistRepository.save(specialist);
    }

    @Override
    public void removeSubServiceFromSpecialist(Long specialistId, Long subServiceId) {
        SpecialistEntity specialist = getSpecialistById(specialistId);
        specialist.getSubServices().remove(subServiceServiceImpl.getSubServiceById(subServiceId));
        specialistRepository.save(specialist);
    }
}