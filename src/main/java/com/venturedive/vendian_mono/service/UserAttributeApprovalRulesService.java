package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.UserAttributeApprovalRules;
import com.venturedive.vendian_mono.repository.UserAttributeApprovalRulesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserAttributeApprovalRules}.
 */
@Service
@Transactional
public class UserAttributeApprovalRulesService {

    private final Logger log = LoggerFactory.getLogger(UserAttributeApprovalRulesService.class);

    private final UserAttributeApprovalRulesRepository userAttributeApprovalRulesRepository;

    public UserAttributeApprovalRulesService(UserAttributeApprovalRulesRepository userAttributeApprovalRulesRepository) {
        this.userAttributeApprovalRulesRepository = userAttributeApprovalRulesRepository;
    }

    /**
     * Save a userAttributeApprovalRules.
     *
     * @param userAttributeApprovalRules the entity to save.
     * @return the persisted entity.
     */
    public UserAttributeApprovalRules save(UserAttributeApprovalRules userAttributeApprovalRules) {
        log.debug("Request to save UserAttributeApprovalRules : {}", userAttributeApprovalRules);
        return userAttributeApprovalRulesRepository.save(userAttributeApprovalRules);
    }

    /**
     * Update a userAttributeApprovalRules.
     *
     * @param userAttributeApprovalRules the entity to save.
     * @return the persisted entity.
     */
    public UserAttributeApprovalRules update(UserAttributeApprovalRules userAttributeApprovalRules) {
        log.debug("Request to update UserAttributeApprovalRules : {}", userAttributeApprovalRules);
        return userAttributeApprovalRulesRepository.save(userAttributeApprovalRules);
    }

    /**
     * Partially update a userAttributeApprovalRules.
     *
     * @param userAttributeApprovalRules the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserAttributeApprovalRules> partialUpdate(UserAttributeApprovalRules userAttributeApprovalRules) {
        log.debug("Request to partially update UserAttributeApprovalRules : {}", userAttributeApprovalRules);

        return userAttributeApprovalRulesRepository
            .findById(userAttributeApprovalRules.getId())
            .map(existingUserAttributeApprovalRules -> {
                if (userAttributeApprovalRules.getEffDate() != null) {
                    existingUserAttributeApprovalRules.setEffDate(userAttributeApprovalRules.getEffDate());
                }
                if (userAttributeApprovalRules.getCreatedAt() != null) {
                    existingUserAttributeApprovalRules.setCreatedAt(userAttributeApprovalRules.getCreatedAt());
                }
                if (userAttributeApprovalRules.getUpdatedAt() != null) {
                    existingUserAttributeApprovalRules.setUpdatedAt(userAttributeApprovalRules.getUpdatedAt());
                }
                if (userAttributeApprovalRules.getEndDate() != null) {
                    existingUserAttributeApprovalRules.setEndDate(userAttributeApprovalRules.getEndDate());
                }
                if (userAttributeApprovalRules.getVersion() != null) {
                    existingUserAttributeApprovalRules.setVersion(userAttributeApprovalRules.getVersion());
                }

                return existingUserAttributeApprovalRules;
            })
            .map(userAttributeApprovalRulesRepository::save);
    }

    /**
     * Get all the userAttributeApprovalRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAttributeApprovalRules> findAll(Pageable pageable) {
        log.debug("Request to get all UserAttributeApprovalRules");
        return userAttributeApprovalRulesRepository.findAll(pageable);
    }

    /**
     * Get one userAttributeApprovalRules by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserAttributeApprovalRules> findOne(Long id) {
        log.debug("Request to get UserAttributeApprovalRules : {}", id);
        return userAttributeApprovalRulesRepository.findById(id);
    }

    /**
     * Delete the userAttributeApprovalRules by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserAttributeApprovalRules : {}", id);
        userAttributeApprovalRulesRepository.deleteById(id);
    }
}
