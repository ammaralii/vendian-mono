package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.HrPerformanceCycles;
import com.venturedive.vendian_mono.repository.HrPerformanceCyclesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HrPerformanceCycles}.
 */
@Service
@Transactional
public class HrPerformanceCyclesService {

    private final Logger log = LoggerFactory.getLogger(HrPerformanceCyclesService.class);

    private final HrPerformanceCyclesRepository hrPerformanceCyclesRepository;

    public HrPerformanceCyclesService(HrPerformanceCyclesRepository hrPerformanceCyclesRepository) {
        this.hrPerformanceCyclesRepository = hrPerformanceCyclesRepository;
    }

    /**
     * Save a hrPerformanceCycles.
     *
     * @param hrPerformanceCycles the entity to save.
     * @return the persisted entity.
     */
    public HrPerformanceCycles save(HrPerformanceCycles hrPerformanceCycles) {
        log.debug("Request to save HrPerformanceCycles : {}", hrPerformanceCycles);
        return hrPerformanceCyclesRepository.save(hrPerformanceCycles);
    }

    /**
     * Update a hrPerformanceCycles.
     *
     * @param hrPerformanceCycles the entity to save.
     * @return the persisted entity.
     */
    public HrPerformanceCycles update(HrPerformanceCycles hrPerformanceCycles) {
        log.debug("Request to update HrPerformanceCycles : {}", hrPerformanceCycles);
        return hrPerformanceCyclesRepository.save(hrPerformanceCycles);
    }

    /**
     * Partially update a hrPerformanceCycles.
     *
     * @param hrPerformanceCycles the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HrPerformanceCycles> partialUpdate(HrPerformanceCycles hrPerformanceCycles) {
        log.debug("Request to partially update HrPerformanceCycles : {}", hrPerformanceCycles);

        return hrPerformanceCyclesRepository
            .findById(hrPerformanceCycles.getId())
            .map(existingHrPerformanceCycles -> {
                if (hrPerformanceCycles.getTitle() != null) {
                    existingHrPerformanceCycles.setTitle(hrPerformanceCycles.getTitle());
                }
                if (hrPerformanceCycles.getFreeze() != null) {
                    existingHrPerformanceCycles.setFreeze(hrPerformanceCycles.getFreeze());
                }
                if (hrPerformanceCycles.getDueDate() != null) {
                    existingHrPerformanceCycles.setDueDate(hrPerformanceCycles.getDueDate());
                }
                if (hrPerformanceCycles.getStartDate() != null) {
                    existingHrPerformanceCycles.setStartDate(hrPerformanceCycles.getStartDate());
                }
                if (hrPerformanceCycles.getEndDate() != null) {
                    existingHrPerformanceCycles.setEndDate(hrPerformanceCycles.getEndDate());
                }
                if (hrPerformanceCycles.getCreatedAt() != null) {
                    existingHrPerformanceCycles.setCreatedAt(hrPerformanceCycles.getCreatedAt());
                }
                if (hrPerformanceCycles.getUpdatedAt() != null) {
                    existingHrPerformanceCycles.setUpdatedAt(hrPerformanceCycles.getUpdatedAt());
                }
                if (hrPerformanceCycles.getDeletedAt() != null) {
                    existingHrPerformanceCycles.setDeletedAt(hrPerformanceCycles.getDeletedAt());
                }
                if (hrPerformanceCycles.getVersion() != null) {
                    existingHrPerformanceCycles.setVersion(hrPerformanceCycles.getVersion());
                }

                return existingHrPerformanceCycles;
            })
            .map(hrPerformanceCyclesRepository::save);
    }

    /**
     * Get all the hrPerformanceCycles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HrPerformanceCycles> findAll(Pageable pageable) {
        log.debug("Request to get all HrPerformanceCycles");
        return hrPerformanceCyclesRepository.findAll(pageable);
    }

    /**
     * Get one hrPerformanceCycles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HrPerformanceCycles> findOne(Long id) {
        log.debug("Request to get HrPerformanceCycles : {}", id);
        return hrPerformanceCyclesRepository.findById(id);
    }

    /**
     * Delete the hrPerformanceCycles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HrPerformanceCycles : {}", id);
        hrPerformanceCyclesRepository.deleteById(id);
    }
}
