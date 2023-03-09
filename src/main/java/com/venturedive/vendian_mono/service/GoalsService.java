package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Goals;
import com.venturedive.vendian_mono.repository.GoalsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Goals}.
 */
@Service
@Transactional
public class GoalsService {

    private final Logger log = LoggerFactory.getLogger(GoalsService.class);

    private final GoalsRepository goalsRepository;

    public GoalsService(GoalsRepository goalsRepository) {
        this.goalsRepository = goalsRepository;
    }

    /**
     * Save a goals.
     *
     * @param goals the entity to save.
     * @return the persisted entity.
     */
    public Goals save(Goals goals) {
        log.debug("Request to save Goals : {}", goals);
        return goalsRepository.save(goals);
    }

    /**
     * Update a goals.
     *
     * @param goals the entity to save.
     * @return the persisted entity.
     */
    public Goals update(Goals goals) {
        log.debug("Request to update Goals : {}", goals);
        return goalsRepository.save(goals);
    }

    /**
     * Partially update a goals.
     *
     * @param goals the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Goals> partialUpdate(Goals goals) {
        log.debug("Request to partially update Goals : {}", goals);

        return goalsRepository
            .findById(goals.getId())
            .map(existingGoals -> {
                if (goals.getTitle() != null) {
                    existingGoals.setTitle(goals.getTitle());
                }
                if (goals.getDescription() != null) {
                    existingGoals.setDescription(goals.getDescription());
                }
                if (goals.getMeasurement() != null) {
                    existingGoals.setMeasurement(goals.getMeasurement());
                }
                if (goals.getCreatedAt() != null) {
                    existingGoals.setCreatedAt(goals.getCreatedAt());
                }
                if (goals.getUpdatedAt() != null) {
                    existingGoals.setUpdatedAt(goals.getUpdatedAt());
                }
                if (goals.getDeletedAt() != null) {
                    existingGoals.setDeletedAt(goals.getDeletedAt());
                }
                if (goals.getVersion() != null) {
                    existingGoals.setVersion(goals.getVersion());
                }

                return existingGoals;
            })
            .map(goalsRepository::save);
    }

    /**
     * Get all the goals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Goals> findAll(Pageable pageable) {
        log.debug("Request to get all Goals");
        return goalsRepository.findAll(pageable);
    }

    /**
     * Get one goals by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Goals> findOne(Long id) {
        log.debug("Request to get Goals : {}", id);
        return goalsRepository.findById(id);
    }

    /**
     * Delete the goals by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Goals : {}", id);
        goalsRepository.deleteById(id);
    }
}
