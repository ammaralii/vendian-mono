package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeEducation;
import com.venturedive.vendian_mono.repository.EmployeeEducationRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeEducation}.
 */
@Service
@Transactional
public class EmployeeEducationService {

    private final Logger log = LoggerFactory.getLogger(EmployeeEducationService.class);

    private final EmployeeEducationRepository employeeEducationRepository;

    public EmployeeEducationService(EmployeeEducationRepository employeeEducationRepository) {
        this.employeeEducationRepository = employeeEducationRepository;
    }

    /**
     * Save a employeeEducation.
     *
     * @param employeeEducation the entity to save.
     * @return the persisted entity.
     */
    public EmployeeEducation save(EmployeeEducation employeeEducation) {
        log.debug("Request to save EmployeeEducation : {}", employeeEducation);
        return employeeEducationRepository.save(employeeEducation);
    }

    /**
     * Update a employeeEducation.
     *
     * @param employeeEducation the entity to save.
     * @return the persisted entity.
     */
    public EmployeeEducation update(EmployeeEducation employeeEducation) {
        log.debug("Request to update EmployeeEducation : {}", employeeEducation);
        return employeeEducationRepository.save(employeeEducation);
    }

    /**
     * Partially update a employeeEducation.
     *
     * @param employeeEducation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeEducation> partialUpdate(EmployeeEducation employeeEducation) {
        log.debug("Request to partially update EmployeeEducation : {}", employeeEducation);

        return employeeEducationRepository
            .findById(employeeEducation.getId())
            .map(existingEmployeeEducation -> {
                if (employeeEducation.getInstitute() != null) {
                    existingEmployeeEducation.setInstitute(employeeEducation.getInstitute());
                }
                if (employeeEducation.getMajor() != null) {
                    existingEmployeeEducation.setMajor(employeeEducation.getMajor());
                }
                if (employeeEducation.getDegree() != null) {
                    existingEmployeeEducation.setDegree(employeeEducation.getDegree());
                }
                if (employeeEducation.getValue() != null) {
                    existingEmployeeEducation.setValue(employeeEducation.getValue());
                }
                if (employeeEducation.getCity() != null) {
                    existingEmployeeEducation.setCity(employeeEducation.getCity());
                }
                if (employeeEducation.getProvince() != null) {
                    existingEmployeeEducation.setProvince(employeeEducation.getProvince());
                }
                if (employeeEducation.getCountry() != null) {
                    existingEmployeeEducation.setCountry(employeeEducation.getCountry());
                }
                if (employeeEducation.getDatefrom() != null) {
                    existingEmployeeEducation.setDatefrom(employeeEducation.getDatefrom());
                }
                if (employeeEducation.getDateto() != null) {
                    existingEmployeeEducation.setDateto(employeeEducation.getDateto());
                }
                if (employeeEducation.getCreatedat() != null) {
                    existingEmployeeEducation.setCreatedat(employeeEducation.getCreatedat());
                }
                if (employeeEducation.getUpdatedat() != null) {
                    existingEmployeeEducation.setUpdatedat(employeeEducation.getUpdatedat());
                }

                return existingEmployeeEducation;
            })
            .map(employeeEducationRepository::save);
    }

    /**
     * Get all the employeeEducations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeEducation> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeEducations");
        return employeeEducationRepository.findAll(pageable);
    }

    /**
     * Get one employeeEducation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeEducation> findOne(Long id) {
        log.debug("Request to get EmployeeEducation : {}", id);
        return employeeEducationRepository.findById(id);
    }

    /**
     * Delete the employeeEducation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeEducation : {}", id);
        employeeEducationRepository.deleteById(id);
    }
}
