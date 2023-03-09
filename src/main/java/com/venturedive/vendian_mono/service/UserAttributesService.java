package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.UserAttributes;
import com.venturedive.vendian_mono.repository.UserAttributesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserAttributes}.
 */
@Service
@Transactional
public class UserAttributesService {

    private final Logger log = LoggerFactory.getLogger(UserAttributesService.class);

    private final UserAttributesRepository userAttributesRepository;

    public UserAttributesService(UserAttributesRepository userAttributesRepository) {
        this.userAttributesRepository = userAttributesRepository;
    }

    /**
     * Save a userAttributes.
     *
     * @param userAttributes the entity to save.
     * @return the persisted entity.
     */
    public UserAttributes save(UserAttributes userAttributes) {
        log.debug("Request to save UserAttributes : {}", userAttributes);
        return userAttributesRepository.save(userAttributes);
    }

    /**
     * Update a userAttributes.
     *
     * @param userAttributes the entity to save.
     * @return the persisted entity.
     */
    public UserAttributes update(UserAttributes userAttributes) {
        log.debug("Request to update UserAttributes : {}", userAttributes);
        return userAttributesRepository.save(userAttributes);
    }

    /**
     * Partially update a userAttributes.
     *
     * @param userAttributes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserAttributes> partialUpdate(UserAttributes userAttributes) {
        log.debug("Request to partially update UserAttributes : {}", userAttributes);

        return userAttributesRepository
            .findById(userAttributes.getId())
            .map(existingUserAttributes -> {
                if (userAttributes.getCreatedAt() != null) {
                    existingUserAttributes.setCreatedAt(userAttributes.getCreatedAt());
                }
                if (userAttributes.getUpdatedAt() != null) {
                    existingUserAttributes.setUpdatedAt(userAttributes.getUpdatedAt());
                }
                if (userAttributes.getVersion() != null) {
                    existingUserAttributes.setVersion(userAttributes.getVersion());
                }
                if (userAttributes.getEndDate() != null) {
                    existingUserAttributes.setEndDate(userAttributes.getEndDate());
                }
                if (userAttributes.getEffDate() != null) {
                    existingUserAttributes.setEffDate(userAttributes.getEffDate());
                }

                return existingUserAttributes;
            })
            .map(userAttributesRepository::save);
    }

    /**
     * Get all the userAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAttributes> findAll(Pageable pageable) {
        log.debug("Request to get all UserAttributes");
        return userAttributesRepository.findAll(pageable);
    }

    /**
     * Get one userAttributes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserAttributes> findOne(Long id) {
        log.debug("Request to get UserAttributes : {}", id);
        return userAttributesRepository.findById(id);
    }

    /**
     * Delete the userAttributes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserAttributes : {}", id);
        userAttributesRepository.deleteById(id);
    }
}
