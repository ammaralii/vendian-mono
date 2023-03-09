package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeWorks;
import com.venturedive.vendian_mono.repository.EmployeeWorksRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeWorks}.
 */
@Service
@Transactional
public class EmployeeWorksService {

    private final Logger log = LoggerFactory.getLogger(EmployeeWorksService.class);

    private final EmployeeWorksRepository employeeWorksRepository;

    public EmployeeWorksService(EmployeeWorksRepository employeeWorksRepository) {
        this.employeeWorksRepository = employeeWorksRepository;
    }

    /**
     * Save a employeeWorks.
     *
     * @param employeeWorks the entity to save.
     * @return the persisted entity.
     */
    public EmployeeWorks save(EmployeeWorks employeeWorks) {
        log.debug("Request to save EmployeeWorks : {}", employeeWorks);
        return employeeWorksRepository.save(employeeWorks);
    }

    /**
     * Update a employeeWorks.
     *
     * @param employeeWorks the entity to save.
     * @return the persisted entity.
     */
    public EmployeeWorks update(EmployeeWorks employeeWorks) {
        log.debug("Request to update EmployeeWorks : {}", employeeWorks);
        return employeeWorksRepository.save(employeeWorks);
    }

    /**
     * Partially update a employeeWorks.
     *
     * @param employeeWorks the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeWorks> partialUpdate(EmployeeWorks employeeWorks) {
        log.debug("Request to partially update EmployeeWorks : {}", employeeWorks);

        return employeeWorksRepository
            .findById(employeeWorks.getId())
            .map(existingEmployeeWorks -> {
                if (employeeWorks.getStartdate() != null) {
                    existingEmployeeWorks.setStartdate(employeeWorks.getStartdate());
                }
                if (employeeWorks.getEnddate() != null) {
                    existingEmployeeWorks.setEnddate(employeeWorks.getEnddate());
                }
                if (employeeWorks.getDesignation() != null) {
                    existingEmployeeWorks.setDesignation(employeeWorks.getDesignation());
                }
                if (employeeWorks.getLeavingreason() != null) {
                    existingEmployeeWorks.setLeavingreason(employeeWorks.getLeavingreason());
                }
                if (employeeWorks.getCreatedat() != null) {
                    existingEmployeeWorks.setCreatedat(employeeWorks.getCreatedat());
                }
                if (employeeWorks.getUpdatedat() != null) {
                    existingEmployeeWorks.setUpdatedat(employeeWorks.getUpdatedat());
                }

                return existingEmployeeWorks;
            })
            .map(employeeWorksRepository::save);
    }

    /**
     * Get all the employeeWorks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeWorks> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeWorks");
        return employeeWorksRepository.findAll(pageable);
    }

    /**
     * Get one employeeWorks by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeWorks> findOne(Long id) {
        log.debug("Request to get EmployeeWorks : {}", id);
        return employeeWorksRepository.findById(id);
    }

    /**
     * Delete the employeeWorks by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeWorks : {}", id);
        employeeWorksRepository.deleteById(id);
    }
}
