package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmploymentTypes;
import com.venturedive.vendian_mono.repository.EmploymentTypesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmploymentTypes}.
 */
@Service
@Transactional
public class EmploymentTypesService {

    private final Logger log = LoggerFactory.getLogger(EmploymentTypesService.class);

    private final EmploymentTypesRepository employmentTypesRepository;

    public EmploymentTypesService(EmploymentTypesRepository employmentTypesRepository) {
        this.employmentTypesRepository = employmentTypesRepository;
    }

    /**
     * Save a employmentTypes.
     *
     * @param employmentTypes the entity to save.
     * @return the persisted entity.
     */
    public EmploymentTypes save(EmploymentTypes employmentTypes) {
        log.debug("Request to save EmploymentTypes : {}", employmentTypes);
        return employmentTypesRepository.save(employmentTypes);
    }

    /**
     * Update a employmentTypes.
     *
     * @param employmentTypes the entity to save.
     * @return the persisted entity.
     */
    public EmploymentTypes update(EmploymentTypes employmentTypes) {
        log.debug("Request to update EmploymentTypes : {}", employmentTypes);
        return employmentTypesRepository.save(employmentTypes);
    }

    /**
     * Partially update a employmentTypes.
     *
     * @param employmentTypes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmploymentTypes> partialUpdate(EmploymentTypes employmentTypes) {
        log.debug("Request to partially update EmploymentTypes : {}", employmentTypes);

        return employmentTypesRepository
            .findById(employmentTypes.getId())
            .map(existingEmploymentTypes -> {
                if (employmentTypes.getName() != null) {
                    existingEmploymentTypes.setName(employmentTypes.getName());
                }
                if (employmentTypes.getCreatedat() != null) {
                    existingEmploymentTypes.setCreatedat(employmentTypes.getCreatedat());
                }
                if (employmentTypes.getUpdatedat() != null) {
                    existingEmploymentTypes.setUpdatedat(employmentTypes.getUpdatedat());
                }

                return existingEmploymentTypes;
            })
            .map(employmentTypesRepository::save);
    }

    /**
     * Get all the employmentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmploymentTypes> findAll(Pageable pageable) {
        log.debug("Request to get all EmploymentTypes");
        return employmentTypesRepository.findAll(pageable);
    }

    /**
     * Get one employmentTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmploymentTypes> findOne(Long id) {
        log.debug("Request to get EmploymentTypes : {}", id);
        return employmentTypesRepository.findById(id);
    }

    /**
     * Delete the employmentTypes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmploymentTypes : {}", id);
        employmentTypesRepository.deleteById(id);
    }
}
