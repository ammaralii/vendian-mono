package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.UserRelationApprovalRules;
import com.venturedive.vendian_mono.repository.UserRelationApprovalRulesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserRelationApprovalRules}.
 */
@Service
@Transactional
public class UserRelationApprovalRulesService {

    private final Logger log = LoggerFactory.getLogger(UserRelationApprovalRulesService.class);

    private final UserRelationApprovalRulesRepository userRelationApprovalRulesRepository;

    public UserRelationApprovalRulesService(UserRelationApprovalRulesRepository userRelationApprovalRulesRepository) {
        this.userRelationApprovalRulesRepository = userRelationApprovalRulesRepository;
    }

    /**
     * Save a userRelationApprovalRules.
     *
     * @param userRelationApprovalRules the entity to save.
     * @return the persisted entity.
     */
    public UserRelationApprovalRules save(UserRelationApprovalRules userRelationApprovalRules) {
        log.debug("Request to save UserRelationApprovalRules : {}", userRelationApprovalRules);
        return userRelationApprovalRulesRepository.save(userRelationApprovalRules);
    }

    /**
     * Update a userRelationApprovalRules.
     *
     * @param userRelationApprovalRules the entity to save.
     * @return the persisted entity.
     */
    public UserRelationApprovalRules update(UserRelationApprovalRules userRelationApprovalRules) {
        log.debug("Request to update UserRelationApprovalRules : {}", userRelationApprovalRules);
        return userRelationApprovalRulesRepository.save(userRelationApprovalRules);
    }

    /**
     * Partially update a userRelationApprovalRules.
     *
     * @param userRelationApprovalRules the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserRelationApprovalRules> partialUpdate(UserRelationApprovalRules userRelationApprovalRules) {
        log.debug("Request to partially update UserRelationApprovalRules : {}", userRelationApprovalRules);

        return userRelationApprovalRulesRepository
            .findById(userRelationApprovalRules.getId())
            .map(existingUserRelationApprovalRules -> {
                if (userRelationApprovalRules.getEffDate() != null) {
                    existingUserRelationApprovalRules.setEffDate(userRelationApprovalRules.getEffDate());
                }
                if (userRelationApprovalRules.getCreatedAt() != null) {
                    existingUserRelationApprovalRules.setCreatedAt(userRelationApprovalRules.getCreatedAt());
                }
                if (userRelationApprovalRules.getUpdatedAt() != null) {
                    existingUserRelationApprovalRules.setUpdatedAt(userRelationApprovalRules.getUpdatedAt());
                }
                if (userRelationApprovalRules.getEndDate() != null) {
                    existingUserRelationApprovalRules.setEndDate(userRelationApprovalRules.getEndDate());
                }
                if (userRelationApprovalRules.getVersion() != null) {
                    existingUserRelationApprovalRules.setVersion(userRelationApprovalRules.getVersion());
                }

                return existingUserRelationApprovalRules;
            })
            .map(userRelationApprovalRulesRepository::save);
    }

    /**
     * Get all the userRelationApprovalRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserRelationApprovalRules> findAll(Pageable pageable) {
        log.debug("Request to get all UserRelationApprovalRules");
        return userRelationApprovalRulesRepository.findAll(pageable);
    }

    /**
     * Get one userRelationApprovalRules by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserRelationApprovalRules> findOne(Long id) {
        log.debug("Request to get UserRelationApprovalRules : {}", id);
        return userRelationApprovalRulesRepository.findById(id);
    }

    /**
     * Delete the userRelationApprovalRules by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserRelationApprovalRules : {}", id);
        userRelationApprovalRulesRepository.deleteById(id);
    }
}
