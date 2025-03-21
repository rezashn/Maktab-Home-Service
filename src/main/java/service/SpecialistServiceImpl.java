package service;

import entity.Specialist;
import exception.ResponseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SpecialistRepository;

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

    public Specialist addSpecialist(Specialist specialist) {
        userServiceImpl.getUserById(specialist.getUser().getId());
        return specialistRepository.save(specialist);
    }

    public Specialist updateSpecialist(Long specialistId, Specialist specialistDetails) {
        Specialist existingSpecialist = getSpecialistById(specialistId);
        existingSpecialist.setUser(userServiceImpl.getUserById(specialistDetails.getUser().getId()));
        existingSpecialist.setProfilePicture(specialistDetails.getProfilePicture());
        existingSpecialist.setRating(specialistDetails.getRating());
        existingSpecialist.setSubServices(specialistDetails.getSubServices());
        return specialistRepository.save(existingSpecialist);
    }

    public void deleteSpecialist(Long specialistId) {
        if (!specialistRepository.existsById(specialistId)) {
            throw new ResponseNotFoundException("specialist not found");
        }
        specialistRepository.deleteById(specialistId);
    }

    public Specialist getSpecialistById(Long specialistId) {
        return specialistRepository.findById(specialistId).orElseThrow(() -> new ResponseNotFoundException("specialist not found"));
    }

    public List<Specialist> getAllSpecialists() {
        return specialistRepository.findAll();
    }

    public void addSubServiceToSpecialist(Long specialistId, Long subServiceId) {
        Specialist specialist = getSpecialistById(specialistId);
        specialist.getSubServices().add(subServiceServiceImpl.getSubServiceById(subServiceId));
        specialistRepository.save(specialist);
    }

    public void removeSubServiceFromSpecialist(Long specialistId, Long subServiceId) {
        Specialist specialist = getSpecialistById(specialistId);
        specialist.getSubServices().remove(subServiceServiceImpl.getSubServiceById(subServiceId));
        specialistRepository.save(specialist);
    }
}