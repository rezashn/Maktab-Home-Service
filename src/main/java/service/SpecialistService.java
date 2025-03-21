package service;

import entity.Specialist;

import java.util.List;

public interface SpecialistService {
    Specialist addSpecialist(Specialist specialist);

    Specialist updateSpecialist(Long specialistId, Specialist specialistDetails);

    void deleteSpecialist(Long specialistId);

    Specialist getSpecialistById(Long specialistId);

    List<Specialist> getAllSpecialists();

    void addSubServiceToSpecialist(Long specialistId, Long subServiceId);

    void removeSubServiceFromSpecialist(Long specialistId, Long subServiceId);
}
