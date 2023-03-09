package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.UserAttributeEscalationRules;
import com.venturedive.vendian_mono.repository.UserAttributeEscalationRulesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserAttributeEscalationRules}.
 */
@Service
@Transactional
public class UserAttributeEscalationRulesService {

    private final Logger log = LoggerFactory.getLogger(UserAttributeEscalationRulesService.class);

    private final UserAttributeEscalationRulesRepository userAttributeEscalationRulesRepository;

    public UserAttributeEscalationRulesService(UserAttributeEscalationRulesRepository userAttributeEscalationRulesRepository) {
        this.userAttributeEscalationRulesRepository = userAttributeEscalationRulesRepository;
    }

    /**
     * Save a userAttributeEscalationRules.
     *
     * @param userAttributeEscalationRules the entity to save.
     * @return the persisted entity.
     */
    public UserAttributeEscalationRules save(UserAttributeEscalationRules userAttributeEscalationRules) {
        log.debug("Request to save UserAttributeEscalationRules : {}", userAttributeEscalationRules);
        return userAttributeEscalationRulesRepository.save(userAttributeEscalationRules);
    }

    /**
     * Update a userAttributeEscalationRules.
     *
     * @param userAttributeEscalationRules the entity to save.
     * @return the persisted entity.
     */
    public UserAttributeEscalationRules update(UserAttributeEscalationRules userAttributeEscalationRules) {
        log.debug("Request to update UserAttributeEscalationRules : {}", userAttributeEscalationRules);
        return userAttributeEscalationRulesRepository.save(userAttributeEscalationRules);
    }

    /**
     * Partially update a userAttributeEscalationRules.
     *
     * @param userAttributeEscalationRules the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserAttributeEscalationRules> partialUpdate(UserAttributeEscalationRules userAttributeEscalationRules) {
        log.debug("Request to partially update UserAttributeEscalationRules : {}", userAttributeEscalationRules);

        return userAttributeEscalationRulesRepository
            .findById(userAttributeEscalationRules.getId())
            .map(existingUserAttributeEscalationRules -> {
                if (userAttributeEscalationRules.getLeaveEscalationCriteriaId() != null) {
                    existingUserAttributeEscalationRules.setLeaveEscalationCriteriaId(
                        userAttributeEscalationRules.getLeaveEscalationCriteriaId()
                    );
                }
                if (userAttributeEscalationRules.getNoOfDays() != null) {
                    existingUserAttributeEscalationRules.setNoOfDays(userAttributeEscalationRules.getNoOfDays());
                }
                if (userAttributeEscalationRules.getEffDate() != null) {
                    existingUserAttributeEscalationRules.setEffDate(userAttributeEscalationRules.getEffDate());
                }
                if (userAttributeEscalationRules.getCreatedAt() != null) {
                    existingUserAttributeEscalationRules.setCreatedAt(userAttributeEscalationRules.getCreatedAt());
                }
                if (userAttributeEscalationRules.getUpdatedAt() != null) {
                    existingUserAttributeEscalationRules.setUpdatedAt(userAttributeEscalationRules.getUpdatedAt());
                }
                if (userAttributeEscalationRules.getEndDate() != null) {
                    existingUserAttributeEscalationRules.setEndDate(userAttributeEscalationRules.getEndDate());
                }
                if (userAttributeEscalationRules.getVersion() != null) {
                    existingUserAttributeEscalationRules.setVersion(userAttributeEscalationRules.getVersion());
                }

                return existingUserAttributeEscalationRules;
            })
            .map(userAttributeEscalationRulesRepository::save);
    }

    /**
     * Get all the userAttributeEscalationRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAttributeEscalationRules> findAll(Pageable pageable) {
        log.debug("Request to get all UserAttributeEscalationRules");
        return userAttributeEscalationRulesRepository.findAll(pageable);
    }

    /**
     * Get one userAttributeEscalationRules by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserAttributeEscalationRules> findOne(Long id) {
        log.debug("Request to get UserAttributeEscalationRules : {}", id);
        return userAttributeEscalationRulesRepository.findById(id);
    }

    /**
     * Delete the userAttributeEscalationRules by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserAttributeEscalationRules : {}", id);
        userAttributeEscalationRulesRepository.deleteById(id);
    }
}
