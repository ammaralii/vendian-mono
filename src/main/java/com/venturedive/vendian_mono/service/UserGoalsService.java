package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.UserGoals;
import com.venturedive.vendian_mono.repository.UserGoalsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserGoals}.
 */
@Service
@Transactional
public class UserGoalsService {

    private final Logger log = LoggerFactory.getLogger(UserGoalsService.class);

    private final UserGoalsRepository userGoalsRepository;

    public UserGoalsService(UserGoalsRepository userGoalsRepository) {
        this.userGoalsRepository = userGoalsRepository;
    }

    /**
     * Save a userGoals.
     *
     * @param userGoals the entity to save.
     * @return the persisted entity.
     */
    public UserGoals save(UserGoals userGoals) {
        log.debug("Request to save UserGoals : {}", userGoals);
        return userGoalsRepository.save(userGoals);
    }

    /**
     * Update a userGoals.
     *
     * @param userGoals the entity to save.
     * @return the persisted entity.
     */
    public UserGoals update(UserGoals userGoals) {
        log.debug("Request to update UserGoals : {}", userGoals);
        return userGoalsRepository.save(userGoals);
    }

    /**
     * Partially update a userGoals.
     *
     * @param userGoals the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserGoals> partialUpdate(UserGoals userGoals) {
        log.debug("Request to partially update UserGoals : {}", userGoals);

        return userGoalsRepository
            .findById(userGoals.getId())
            .map(existingUserGoals -> {
                if (userGoals.getTitle() != null) {
                    existingUserGoals.setTitle(userGoals.getTitle());
                }
                if (userGoals.getDescription() != null) {
                    existingUserGoals.setDescription(userGoals.getDescription());
                }
                if (userGoals.getMeasurement() != null) {
                    existingUserGoals.setMeasurement(userGoals.getMeasurement());
                }
                if (userGoals.getWeightage() != null) {
                    existingUserGoals.setWeightage(userGoals.getWeightage());
                }
                if (userGoals.getProgress() != null) {
                    existingUserGoals.setProgress(userGoals.getProgress());
                }
                if (userGoals.getStatus() != null) {
                    existingUserGoals.setStatus(userGoals.getStatus());
                }
                if (userGoals.getDueDate() != null) {
                    existingUserGoals.setDueDate(userGoals.getDueDate());
                }
                if (userGoals.getCreatedAt() != null) {
                    existingUserGoals.setCreatedAt(userGoals.getCreatedAt());
                }
                if (userGoals.getUpdatedAt() != null) {
                    existingUserGoals.setUpdatedAt(userGoals.getUpdatedAt());
                }
                if (userGoals.getDeletedAt() != null) {
                    existingUserGoals.setDeletedAt(userGoals.getDeletedAt());
                }
                if (userGoals.getVersion() != null) {
                    existingUserGoals.setVersion(userGoals.getVersion());
                }

                return existingUserGoals;
            })
            .map(userGoalsRepository::save);
    }

    /**
     * Get all the userGoals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserGoals> findAll(Pageable pageable) {
        log.debug("Request to get all UserGoals");
        return userGoalsRepository.findAll(pageable);
    }

    /**
     * Get one userGoals by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserGoals> findOne(Long id) {
        log.debug("Request to get UserGoals : {}", id);
        return userGoalsRepository.findById(id);
    }

    /**
     * Delete the userGoals by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserGoals : {}", id);
        userGoalsRepository.deleteById(id);
    }
}
