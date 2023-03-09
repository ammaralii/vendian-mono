package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.GoalGroupMappings;
import com.venturedive.vendian_mono.repository.GoalGroupMappingsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GoalGroupMappings}.
 */
@Service
@Transactional
public class GoalGroupMappingsService {

    private final Logger log = LoggerFactory.getLogger(GoalGroupMappingsService.class);

    private final GoalGroupMappingsRepository goalGroupMappingsRepository;

    public GoalGroupMappingsService(GoalGroupMappingsRepository goalGroupMappingsRepository) {
        this.goalGroupMappingsRepository = goalGroupMappingsRepository;
    }

    /**
     * Save a goalGroupMappings.
     *
     * @param goalGroupMappings the entity to save.
     * @return the persisted entity.
     */
    public GoalGroupMappings save(GoalGroupMappings goalGroupMappings) {
        log.debug("Request to save GoalGroupMappings : {}", goalGroupMappings);
        return goalGroupMappingsRepository.save(goalGroupMappings);
    }

    /**
     * Update a goalGroupMappings.
     *
     * @param goalGroupMappings the entity to save.
     * @return the persisted entity.
     */
    public GoalGroupMappings update(GoalGroupMappings goalGroupMappings) {
        log.debug("Request to update GoalGroupMappings : {}", goalGroupMappings);
        return goalGroupMappingsRepository.save(goalGroupMappings);
    }

    /**
     * Partially update a goalGroupMappings.
     *
     * @param goalGroupMappings the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GoalGroupMappings> partialUpdate(GoalGroupMappings goalGroupMappings) {
        log.debug("Request to partially update GoalGroupMappings : {}", goalGroupMappings);

        return goalGroupMappingsRepository
            .findById(goalGroupMappings.getId())
            .map(existingGoalGroupMappings -> {
                if (goalGroupMappings.getWeightage() != null) {
                    existingGoalGroupMappings.setWeightage(goalGroupMappings.getWeightage());
                }
                if (goalGroupMappings.getCreatedAt() != null) {
                    existingGoalGroupMappings.setCreatedAt(goalGroupMappings.getCreatedAt());
                }
                if (goalGroupMappings.getUpdatedAt() != null) {
                    existingGoalGroupMappings.setUpdatedAt(goalGroupMappings.getUpdatedAt());
                }
                if (goalGroupMappings.getDeletedAt() != null) {
                    existingGoalGroupMappings.setDeletedAt(goalGroupMappings.getDeletedAt());
                }
                if (goalGroupMappings.getVersion() != null) {
                    existingGoalGroupMappings.setVersion(goalGroupMappings.getVersion());
                }

                return existingGoalGroupMappings;
            })
            .map(goalGroupMappingsRepository::save);
    }

    /**
     * Get all the goalGroupMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GoalGroupMappings> findAll(Pageable pageable) {
        log.debug("Request to get all GoalGroupMappings");
        return goalGroupMappingsRepository.findAll(pageable);
    }

    /**
     * Get one goalGroupMappings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GoalGroupMappings> findOne(Long id) {
        log.debug("Request to get GoalGroupMappings : {}", id);
        return goalGroupMappingsRepository.findById(id);
    }

    /**
     * Delete the goalGroupMappings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GoalGroupMappings : {}", id);
        goalGroupMappingsRepository.deleteById(id);
    }
}
