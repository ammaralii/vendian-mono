package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeSkills;
import com.venturedive.vendian_mono.repository.EmployeeSkillsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeSkills}.
 */
@Service
@Transactional
public class EmployeeSkillsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillsService.class);

    private final EmployeeSkillsRepository employeeSkillsRepository;

    public EmployeeSkillsService(EmployeeSkillsRepository employeeSkillsRepository) {
        this.employeeSkillsRepository = employeeSkillsRepository;
    }

    /**
     * Save a employeeSkills.
     *
     * @param employeeSkills the entity to save.
     * @return the persisted entity.
     */
    public EmployeeSkills save(EmployeeSkills employeeSkills) {
        log.debug("Request to save EmployeeSkills : {}", employeeSkills);
        return employeeSkillsRepository.save(employeeSkills);
    }

    /**
     * Update a employeeSkills.
     *
     * @param employeeSkills the entity to save.
     * @return the persisted entity.
     */
    public EmployeeSkills update(EmployeeSkills employeeSkills) {
        log.debug("Request to update EmployeeSkills : {}", employeeSkills);
        return employeeSkillsRepository.save(employeeSkills);
    }

    /**
     * Partially update a employeeSkills.
     *
     * @param employeeSkills the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeSkills> partialUpdate(EmployeeSkills employeeSkills) {
        log.debug("Request to partially update EmployeeSkills : {}", employeeSkills);

        return employeeSkillsRepository
            .findById(employeeSkills.getId())
            .map(existingEmployeeSkills -> {
                if (employeeSkills.getCreatedat() != null) {
                    existingEmployeeSkills.setCreatedat(employeeSkills.getCreatedat());
                }
                if (employeeSkills.getUpdatedat() != null) {
                    existingEmployeeSkills.setUpdatedat(employeeSkills.getUpdatedat());
                }
                if (employeeSkills.getExpertise() != null) {
                    existingEmployeeSkills.setExpertise(employeeSkills.getExpertise());
                }

                return existingEmployeeSkills;
            })
            .map(employeeSkillsRepository::save);
    }

    /**
     * Get all the employeeSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSkills> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeSkills");
        return employeeSkillsRepository.findAll(pageable);
    }

    /**
     * Get one employeeSkills by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeSkills> findOne(Long id) {
        log.debug("Request to get EmployeeSkills : {}", id);
        return employeeSkillsRepository.findById(id);
    }

    /**
     * Delete the employeeSkills by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeSkills : {}", id);
        employeeSkillsRepository.deleteById(id);
    }
}
