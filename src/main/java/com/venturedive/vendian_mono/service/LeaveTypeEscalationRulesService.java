package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveTypeEscalationRules;
import com.venturedive.vendian_mono.repository.LeaveTypeEscalationRulesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveTypeEscalationRules}.
 */
@Service
@Transactional
public class LeaveTypeEscalationRulesService {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeEscalationRulesService.class);

    private final LeaveTypeEscalationRulesRepository leaveTypeEscalationRulesRepository;

    public LeaveTypeEscalationRulesService(LeaveTypeEscalationRulesRepository leaveTypeEscalationRulesRepository) {
        this.leaveTypeEscalationRulesRepository = leaveTypeEscalationRulesRepository;
    }

    /**
     * Save a leaveTypeEscalationRules.
     *
     * @param leaveTypeEscalationRules the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypeEscalationRules save(LeaveTypeEscalationRules leaveTypeEscalationRules) {
        log.debug("Request to save LeaveTypeEscalationRules : {}", leaveTypeEscalationRules);
        return leaveTypeEscalationRulesRepository.save(leaveTypeEscalationRules);
    }

    /**
     * Update a leaveTypeEscalationRules.
     *
     * @param leaveTypeEscalationRules the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypeEscalationRules update(LeaveTypeEscalationRules leaveTypeEscalationRules) {
        log.debug("Request to update LeaveTypeEscalationRules : {}", leaveTypeEscalationRules);
        return leaveTypeEscalationRulesRepository.save(leaveTypeEscalationRules);
    }

    /**
     * Partially update a leaveTypeEscalationRules.
     *
     * @param leaveTypeEscalationRules the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveTypeEscalationRules> partialUpdate(LeaveTypeEscalationRules leaveTypeEscalationRules) {
        log.debug("Request to partially update LeaveTypeEscalationRules : {}", leaveTypeEscalationRules);

        return leaveTypeEscalationRulesRepository
            .findById(leaveTypeEscalationRules.getId())
            .map(existingLeaveTypeEscalationRules -> {
                if (leaveTypeEscalationRules.getNoOfDays() != null) {
                    existingLeaveTypeEscalationRules.setNoOfDays(leaveTypeEscalationRules.getNoOfDays());
                }
                if (leaveTypeEscalationRules.getEffDate() != null) {
                    existingLeaveTypeEscalationRules.setEffDate(leaveTypeEscalationRules.getEffDate());
                }
                if (leaveTypeEscalationRules.getCreatedAt() != null) {
                    existingLeaveTypeEscalationRules.setCreatedAt(leaveTypeEscalationRules.getCreatedAt());
                }
                if (leaveTypeEscalationRules.getUpdatedAt() != null) {
                    existingLeaveTypeEscalationRules.setUpdatedAt(leaveTypeEscalationRules.getUpdatedAt());
                }
                if (leaveTypeEscalationRules.getEndDate() != null) {
                    existingLeaveTypeEscalationRules.setEndDate(leaveTypeEscalationRules.getEndDate());
                }
                if (leaveTypeEscalationRules.getVersion() != null) {
                    existingLeaveTypeEscalationRules.setVersion(leaveTypeEscalationRules.getVersion());
                }

                return existingLeaveTypeEscalationRules;
            })
            .map(leaveTypeEscalationRulesRepository::save);
    }

    /**
     * Get all the leaveTypeEscalationRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypeEscalationRules> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveTypeEscalationRules");
        return leaveTypeEscalationRulesRepository.findAll(pageable);
    }

    /**
     * Get one leaveTypeEscalationRules by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveTypeEscalationRules> findOne(Long id) {
        log.debug("Request to get LeaveTypeEscalationRules : {}", id);
        return leaveTypeEscalationRulesRepository.findById(id);
    }

    /**
     * Delete the leaveTypeEscalationRules by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveTypeEscalationRules : {}", id);
        leaveTypeEscalationRulesRepository.deleteById(id);
    }
}
