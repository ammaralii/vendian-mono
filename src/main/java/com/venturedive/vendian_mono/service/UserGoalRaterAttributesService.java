package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.UserGoalRaterAttributes;
import com.venturedive.vendian_mono.repository.UserGoalRaterAttributesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserGoalRaterAttributes}.
 */
@Service
@Transactional
public class UserGoalRaterAttributesService {

    private final Logger log = LoggerFactory.getLogger(UserGoalRaterAttributesService.class);

    private final UserGoalRaterAttributesRepository userGoalRaterAttributesRepository;

    public UserGoalRaterAttributesService(UserGoalRaterAttributesRepository userGoalRaterAttributesRepository) {
        this.userGoalRaterAttributesRepository = userGoalRaterAttributesRepository;
    }

    /**
     * Save a userGoalRaterAttributes.
     *
     * @param userGoalRaterAttributes the entity to save.
     * @return the persisted entity.
     */
    public UserGoalRaterAttributes save(UserGoalRaterAttributes userGoalRaterAttributes) {
        log.debug("Request to save UserGoalRaterAttributes : {}", userGoalRaterAttributes);
        return userGoalRaterAttributesRepository.save(userGoalRaterAttributes);
    }

    /**
     * Update a userGoalRaterAttributes.
     *
     * @param userGoalRaterAttributes the entity to save.
     * @return the persisted entity.
     */
    public UserGoalRaterAttributes update(UserGoalRaterAttributes userGoalRaterAttributes) {
        log.debug("Request to update UserGoalRaterAttributes : {}", userGoalRaterAttributes);
        return userGoalRaterAttributesRepository.save(userGoalRaterAttributes);
    }

    /**
     * Partially update a userGoalRaterAttributes.
     *
     * @param userGoalRaterAttributes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserGoalRaterAttributes> partialUpdate(UserGoalRaterAttributes userGoalRaterAttributes) {
        log.debug("Request to partially update UserGoalRaterAttributes : {}", userGoalRaterAttributes);

        return userGoalRaterAttributesRepository
            .findById(userGoalRaterAttributes.getId())
            .map(existingUserGoalRaterAttributes -> {
                if (userGoalRaterAttributes.getUgraRating() != null) {
                    existingUserGoalRaterAttributes.setUgraRating(userGoalRaterAttributes.getUgraRating());
                }
                if (userGoalRaterAttributes.getUgraRatingContentType() != null) {
                    existingUserGoalRaterAttributes.setUgraRatingContentType(userGoalRaterAttributes.getUgraRatingContentType());
                }
                if (userGoalRaterAttributes.getComment() != null) {
                    existingUserGoalRaterAttributes.setComment(userGoalRaterAttributes.getComment());
                }
                if (userGoalRaterAttributes.getCommentContentType() != null) {
                    existingUserGoalRaterAttributes.setCommentContentType(userGoalRaterAttributes.getCommentContentType());
                }
                if (userGoalRaterAttributes.getCreatedAt() != null) {
                    existingUserGoalRaterAttributes.setCreatedAt(userGoalRaterAttributes.getCreatedAt());
                }
                if (userGoalRaterAttributes.getUpdatedAt() != null) {
                    existingUserGoalRaterAttributes.setUpdatedAt(userGoalRaterAttributes.getUpdatedAt());
                }
                if (userGoalRaterAttributes.getDeletedAt() != null) {
                    existingUserGoalRaterAttributes.setDeletedAt(userGoalRaterAttributes.getDeletedAt());
                }
                if (userGoalRaterAttributes.getVersion() != null) {
                    existingUserGoalRaterAttributes.setVersion(userGoalRaterAttributes.getVersion());
                }

                return existingUserGoalRaterAttributes;
            })
            .map(userGoalRaterAttributesRepository::save);
    }

    /**
     * Get all the userGoalRaterAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserGoalRaterAttributes> findAll(Pageable pageable) {
        log.debug("Request to get all UserGoalRaterAttributes");
        return userGoalRaterAttributesRepository.findAll(pageable);
    }

    /**
     * Get one userGoalRaterAttributes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserGoalRaterAttributes> findOne(Long id) {
        log.debug("Request to get UserGoalRaterAttributes : {}", id);
        return userGoalRaterAttributesRepository.findById(id);
    }

    /**
     * Delete the userGoalRaterAttributes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserGoalRaterAttributes : {}", id);
        userGoalRaterAttributesRepository.deleteById(id);
    }
}
