package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.UserRelations;
import com.venturedive.vendian_mono.repository.UserRelationsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserRelations}.
 */
@Service
@Transactional
public class UserRelationsService {

    private final Logger log = LoggerFactory.getLogger(UserRelationsService.class);

    private final UserRelationsRepository userRelationsRepository;

    public UserRelationsService(UserRelationsRepository userRelationsRepository) {
        this.userRelationsRepository = userRelationsRepository;
    }

    /**
     * Save a userRelations.
     *
     * @param userRelations the entity to save.
     * @return the persisted entity.
     */
    public UserRelations save(UserRelations userRelations) {
        log.debug("Request to save UserRelations : {}", userRelations);
        return userRelationsRepository.save(userRelations);
    }

    /**
     * Update a userRelations.
     *
     * @param userRelations the entity to save.
     * @return the persisted entity.
     */
    public UserRelations update(UserRelations userRelations) {
        log.debug("Request to update UserRelations : {}", userRelations);
        return userRelationsRepository.save(userRelations);
    }

    /**
     * Partially update a userRelations.
     *
     * @param userRelations the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserRelations> partialUpdate(UserRelations userRelations) {
        log.debug("Request to partially update UserRelations : {}", userRelations);

        return userRelationsRepository
            .findById(userRelations.getId())
            .map(existingUserRelations -> {
                if (userRelations.getReferenceType() != null) {
                    existingUserRelations.setReferenceType(userRelations.getReferenceType());
                }
                if (userRelations.getReferenceId() != null) {
                    existingUserRelations.setReferenceId(userRelations.getReferenceId());
                }
                if (userRelations.getEffDate() != null) {
                    existingUserRelations.setEffDate(userRelations.getEffDate());
                }
                if (userRelations.getCreatedAt() != null) {
                    existingUserRelations.setCreatedAt(userRelations.getCreatedAt());
                }
                if (userRelations.getUpdatedAt() != null) {
                    existingUserRelations.setUpdatedAt(userRelations.getUpdatedAt());
                }
                if (userRelations.getEndDate() != null) {
                    existingUserRelations.setEndDate(userRelations.getEndDate());
                }
                if (userRelations.getVersion() != null) {
                    existingUserRelations.setVersion(userRelations.getVersion());
                }

                return existingUserRelations;
            })
            .map(userRelationsRepository::save);
    }

    /**
     * Get all the userRelations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserRelations> findAll(Pageable pageable) {
        log.debug("Request to get all UserRelations");
        return userRelationsRepository.findAll(pageable);
    }

    /**
     * Get one userRelations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserRelations> findOne(Long id) {
        log.debug("Request to get UserRelations : {}", id);
        return userRelationsRepository.findById(id);
    }

    /**
     * Delete the userRelations by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserRelations : {}", id);
        userRelationsRepository.deleteById(id);
    }
}
