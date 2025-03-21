package service;

import entity.Specialist;
import exception.ResponseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SpecialistRepository;

import java.util.List;

@Service
public class SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final SubServiceService subServiceService;
    private final UserService userService;

    @Autowired
    public SpecialistService(SpecialistRepository specialistRepository, SubServiceService subServiceService, UserService userService) {
        this.specialistRepository = specialistRepository;
        this.subServiceService = subServiceService;
        this.userService = userService;
    }

    public Specialist addSpecialist(Specialist specialist) {
        userService.getUserById(specialist.getUser().getId());
        return specialistRepository.save(specialist);
    }

    public Specialist updateSpecialist(Long specialistId, Specialist specialistDetails) {
        Specialist existingSpecialist = getSpecialistById(specialistId);
        existingSpecialist.setUser(userService.getUserById(specialistDetails.getUser().getId()));
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
        specialist.getSubServices().add(subServiceService.getSubServiceById(subServiceId));
        specialistRepository.save(specialist);
    }

    public void removeSubServiceFromSpecialist(Long specialistId, Long subServiceId) {
        Specialist specialist = getSpecialistById(specialistId);
        specialist.getSubServices().remove(subServiceService.getSubServiceById(subServiceId));
        specialistRepository.save(specialist);
    }
}