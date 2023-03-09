package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.WorkLogDetails;
import com.venturedive.vendian_mono.repository.WorkLogDetailsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkLogDetails}.
 */
@Service
@Transactional
public class WorkLogDetailsService {

    private final Logger log = LoggerFactory.getLogger(WorkLogDetailsService.class);

    private final WorkLogDetailsRepository workLogDetailsRepository;

    public WorkLogDetailsService(WorkLogDetailsRepository workLogDetailsRepository) {
        this.workLogDetailsRepository = workLogDetailsRepository;
    }

    /**
     * Save a workLogDetails.
     *
     * @param workLogDetails the entity to save.
     * @return the persisted entity.
     */
    public WorkLogDetails save(WorkLogDetails workLogDetails) {
        log.debug("Request to save WorkLogDetails : {}", workLogDetails);
        return workLogDetailsRepository.save(workLogDetails);
    }

    /**
     * Update a workLogDetails.
     *
     * @param workLogDetails the entity to save.
     * @return the persisted entity.
     */
    public WorkLogDetails update(WorkLogDetails workLogDetails) {
        log.debug("Request to update WorkLogDetails : {}", workLogDetails);
        return workLogDetailsRepository.save(workLogDetails);
    }

    /**
     * Partially update a workLogDetails.
     *
     * @param workLogDetails the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkLogDetails> partialUpdate(WorkLogDetails workLogDetails) {
        log.debug("Request to partially update WorkLogDetails : {}", workLogDetails);

        return workLogDetailsRepository
            .findById(workLogDetails.getId())
            .map(existingWorkLogDetails -> {
                if (workLogDetails.getPercentage() != null) {
                    existingWorkLogDetails.setPercentage(workLogDetails.getPercentage());
                }
                if (workLogDetails.getHours() != null) {
                    existingWorkLogDetails.setHours(workLogDetails.getHours());
                }
                if (workLogDetails.getCreatedat() != null) {
                    existingWorkLogDetails.setCreatedat(workLogDetails.getCreatedat());
                }
                if (workLogDetails.getUpdatedat() != null) {
                    existingWorkLogDetails.setUpdatedat(workLogDetails.getUpdatedat());
                }

                return existingWorkLogDetails;
            })
            .map(workLogDetailsRepository::save);
    }

    /**
     * Get all the workLogDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkLogDetails> findAll(Pageable pageable) {
        log.debug("Request to get all WorkLogDetails");
        return workLogDetailsRepository.findAll(pageable);
    }

    /**
     * Get one workLogDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkLogDetails> findOne(Long id) {
        log.debug("Request to get WorkLogDetails : {}", id);
        return workLogDetailsRepository.findById(id);
    }

    /**
     * Delete the workLogDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkLogDetails : {}", id);
        workLogDetailsRepository.deleteById(id);
    }
}
