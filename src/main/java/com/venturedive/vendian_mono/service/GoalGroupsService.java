package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.GoalGroups;
import com.venturedive.vendian_mono.repository.GoalGroupsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GoalGroups}.
 */
@Service
@Transactional
public class GoalGroupsService {

    private final Logger log = LoggerFactory.getLogger(GoalGroupsService.class);

    private final GoalGroupsRepository goalGroupsRepository;

    public GoalGroupsService(GoalGroupsRepository goalGroupsRepository) {
        this.goalGroupsRepository = goalGroupsRepository;
    }

    /**
     * Save a goalGroups.
     *
     * @param goalGroups the entity to save.
     * @return the persisted entity.
     */
    public GoalGroups save(GoalGroups goalGroups) {
        log.debug("Request to save GoalGroups : {}", goalGroups);
        return goalGroupsRepository.save(goalGroups);
    }

    /**
     * Update a goalGroups.
     *
     * @param goalGroups the entity to save.
     * @return the persisted entity.
     */
    public GoalGroups update(GoalGroups goalGroups) {
        log.debug("Request to update GoalGroups : {}", goalGroups);
        return goalGroupsRepository.save(goalGroups);
    }

    /**
     * Partially update a goalGroups.
     *
     * @param goalGroups the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GoalGroups> partialUpdate(GoalGroups goalGroups) {
        log.debug("Request to partially update GoalGroups : {}", goalGroups);

        return goalGroupsRepository
            .findById(goalGroups.getId())
            .map(existingGoalGroups -> {
                if (goalGroups.getTitle() != null) {
                    existingGoalGroups.setTitle(goalGroups.getTitle());
                }
                if (goalGroups.getDescription() != null) {
                    existingGoalGroups.setDescription(goalGroups.getDescription());
                }
                if (goalGroups.getCreatedAt() != null) {
                    existingGoalGroups.setCreatedAt(goalGroups.getCreatedAt());
                }
                if (goalGroups.getUpdatedAt() != null) {
                    existingGoalGroups.setUpdatedAt(goalGroups.getUpdatedAt());
                }
                if (goalGroups.getDeletedAt() != null) {
                    existingGoalGroups.setDeletedAt(goalGroups.getDeletedAt());
                }
                if (goalGroups.getVersion() != null) {
                    existingGoalGroups.setVersion(goalGroups.getVersion());
                }

                return existingGoalGroups;
            })
            .map(goalGroupsRepository::save);
    }

    /**
     * Get all the goalGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GoalGroups> findAll(Pageable pageable) {
        log.debug("Request to get all GoalGroups");
        return goalGroupsRepository.findAll(pageable);
    }

    /**
     * Get one goalGroups by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GoalGroups> findOne(Long id) {
        log.debug("Request to get GoalGroups : {}", id);
        return goalGroupsRepository.findById(id);
    }

    /**
     * Delete the goalGroups by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GoalGroups : {}", id);
        goalGroupsRepository.deleteById(id);
    }
}
