package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmploymentStatuses;
import com.venturedive.vendian_mono.repository.EmploymentStatusesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmploymentStatuses}.
 */
@Service
@Transactional
public class EmploymentStatusesService {

    private final Logger log = LoggerFactory.getLogger(EmploymentStatusesService.class);

    private final EmploymentStatusesRepository employmentStatusesRepository;

    public EmploymentStatusesService(EmploymentStatusesRepository employmentStatusesRepository) {
        this.employmentStatusesRepository = employmentStatusesRepository;
    }

    /**
     * Save a employmentStatuses.
     *
     * @param employmentStatuses the entity to save.
     * @return the persisted entity.
     */
    public EmploymentStatuses save(EmploymentStatuses employmentStatuses) {
        log.debug("Request to save EmploymentStatuses : {}", employmentStatuses);
        return employmentStatusesRepository.save(employmentStatuses);
    }

    /**
     * Update a employmentStatuses.
     *
     * @param employmentStatuses the entity to save.
     * @return the persisted entity.
     */
    public EmploymentStatuses update(EmploymentStatuses employmentStatuses) {
        log.debug("Request to update EmploymentStatuses : {}", employmentStatuses);
        return employmentStatusesRepository.save(employmentStatuses);
    }

    /**
     * Partially update a employmentStatuses.
     *
     * @param employmentStatuses the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmploymentStatuses> partialUpdate(EmploymentStatuses employmentStatuses) {
        log.debug("Request to partially update EmploymentStatuses : {}", employmentStatuses);

        return employmentStatusesRepository
            .findById(employmentStatuses.getId())
            .map(existingEmploymentStatuses -> {
                if (employmentStatuses.getName() != null) {
                    existingEmploymentStatuses.setName(employmentStatuses.getName());
                }
                if (employmentStatuses.getCreatedat() != null) {
                    existingEmploymentStatuses.setCreatedat(employmentStatuses.getCreatedat());
                }
                if (employmentStatuses.getUpdatedat() != null) {
                    existingEmploymentStatuses.setUpdatedat(employmentStatuses.getUpdatedat());
                }

                return existingEmploymentStatuses;
            })
            .map(employmentStatusesRepository::save);
    }

    /**
     * Get all the employmentStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmploymentStatuses> findAll(Pageable pageable) {
        log.debug("Request to get all EmploymentStatuses");
        return employmentStatusesRepository.findAll(pageable);
    }

    /**
     * Get one employmentStatuses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmploymentStatuses> findOne(Long id) {
        log.debug("Request to get EmploymentStatuses : {}", id);
        return employmentStatusesRepository.findById(id);
    }

    /**
     * Delete the employmentStatuses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmploymentStatuses : {}", id);
        employmentStatusesRepository.deleteById(id);
    }
}
