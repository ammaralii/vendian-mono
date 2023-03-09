package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.UserRatings;
import com.venturedive.vendian_mono.repository.UserRatingsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserRatings}.
 */
@Service
@Transactional
public class UserRatingsService {

    private final Logger log = LoggerFactory.getLogger(UserRatingsService.class);

    private final UserRatingsRepository userRatingsRepository;

    public UserRatingsService(UserRatingsRepository userRatingsRepository) {
        this.userRatingsRepository = userRatingsRepository;
    }

    /**
     * Save a userRatings.
     *
     * @param userRatings the entity to save.
     * @return the persisted entity.
     */
    public UserRatings save(UserRatings userRatings) {
        log.debug("Request to save UserRatings : {}", userRatings);
        return userRatingsRepository.save(userRatings);
    }

    /**
     * Update a userRatings.
     *
     * @param userRatings the entity to save.
     * @return the persisted entity.
     */
    public UserRatings update(UserRatings userRatings) {
        log.debug("Request to update UserRatings : {}", userRatings);
        return userRatingsRepository.save(userRatings);
    }

    /**
     * Partially update a userRatings.
     *
     * @param userRatings the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserRatings> partialUpdate(UserRatings userRatings) {
        log.debug("Request to partially update UserRatings : {}", userRatings);

        return userRatingsRepository
            .findById(userRatings.getId())
            .map(existingUserRatings -> {
                if (userRatings.getRating() != null) {
                    existingUserRatings.setRating(userRatings.getRating());
                }
                if (userRatings.getComment() != null) {
                    existingUserRatings.setComment(userRatings.getComment());
                }
                if (userRatings.getCreatedAt() != null) {
                    existingUserRatings.setCreatedAt(userRatings.getCreatedAt());
                }
                if (userRatings.getUpdatedAt() != null) {
                    existingUserRatings.setUpdatedAt(userRatings.getUpdatedAt());
                }
                if (userRatings.getDeletedAt() != null) {
                    existingUserRatings.setDeletedAt(userRatings.getDeletedAt());
                }
                if (userRatings.getVersion() != null) {
                    existingUserRatings.setVersion(userRatings.getVersion());
                }

                return existingUserRatings;
            })
            .map(userRatingsRepository::save);
    }

    /**
     * Get all the userRatings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserRatings> findAll(Pageable pageable) {
        log.debug("Request to get all UserRatings");
        return userRatingsRepository.findAll(pageable);
    }

    /**
     * Get one userRatings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserRatings> findOne(Long id) {
        log.debug("Request to get UserRatings : {}", id);
        return userRatingsRepository.findById(id);
    }

    /**
     * Delete the userRatings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserRatings : {}", id);
        userRatingsRepository.deleteById(id);
    }
}
