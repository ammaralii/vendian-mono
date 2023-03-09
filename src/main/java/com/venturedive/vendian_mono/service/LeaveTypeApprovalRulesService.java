package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveTypeApprovalRules;
import com.venturedive.vendian_mono.repository.LeaveTypeApprovalRulesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveTypeApprovalRules}.
 */
@Service
@Transactional
public class LeaveTypeApprovalRulesService {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeApprovalRulesService.class);

    private final LeaveTypeApprovalRulesRepository leaveTypeApprovalRulesRepository;

    public LeaveTypeApprovalRulesService(LeaveTypeApprovalRulesRepository leaveTypeApprovalRulesRepository) {
        this.leaveTypeApprovalRulesRepository = leaveTypeApprovalRulesRepository;
    }

    /**
     * Save a leaveTypeApprovalRules.
     *
     * @param leaveTypeApprovalRules the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypeApprovalRules save(LeaveTypeApprovalRules leaveTypeApprovalRules) {
        log.debug("Request to save LeaveTypeApprovalRules : {}", leaveTypeApprovalRules);
        return leaveTypeApprovalRulesRepository.save(leaveTypeApprovalRules);
    }

    /**
     * Update a leaveTypeApprovalRules.
     *
     * @param leaveTypeApprovalRules the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypeApprovalRules update(LeaveTypeApprovalRules leaveTypeApprovalRules) {
        log.debug("Request to update LeaveTypeApprovalRules : {}", leaveTypeApprovalRules);
        return leaveTypeApprovalRulesRepository.save(leaveTypeApprovalRules);
    }

    /**
     * Partially update a leaveTypeApprovalRules.
     *
     * @param leaveTypeApprovalRules the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveTypeApprovalRules> partialUpdate(LeaveTypeApprovalRules leaveTypeApprovalRules) {
        log.debug("Request to partially update LeaveTypeApprovalRules : {}", leaveTypeApprovalRules);

        return leaveTypeApprovalRulesRepository
            .findById(leaveTypeApprovalRules.getId())
            .map(existingLeaveTypeApprovalRules -> {
                if (leaveTypeApprovalRules.getEffDate() != null) {
                    existingLeaveTypeApprovalRules.setEffDate(leaveTypeApprovalRules.getEffDate());
                }
                if (leaveTypeApprovalRules.getCreatedAt() != null) {
                    existingLeaveTypeApprovalRules.setCreatedAt(leaveTypeApprovalRules.getCreatedAt());
                }
                if (leaveTypeApprovalRules.getUpdatedAt() != null) {
                    existingLeaveTypeApprovalRules.setUpdatedAt(leaveTypeApprovalRules.getUpdatedAt());
                }
                if (leaveTypeApprovalRules.getDeletedAt() != null) {
                    existingLeaveTypeApprovalRules.setDeletedAt(leaveTypeApprovalRules.getDeletedAt());
                }
                if (leaveTypeApprovalRules.getVersion() != null) {
                    existingLeaveTypeApprovalRules.setVersion(leaveTypeApprovalRules.getVersion());
                }

                return existingLeaveTypeApprovalRules;
            })
            .map(leaveTypeApprovalRulesRepository::save);
    }

    /**
     * Get all the leaveTypeApprovalRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypeApprovalRules> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveTypeApprovalRules");
        return leaveTypeApprovalRulesRepository.findAll(pageable);
    }

    /**
     * Get one leaveTypeApprovalRules by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveTypeApprovalRules> findOne(Long id) {
        log.debug("Request to get LeaveTypeApprovalRules : {}", id);
        return leaveTypeApprovalRulesRepository.findById(id);
    }

    /**
     * Delete the leaveTypeApprovalRules by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveTypeApprovalRules : {}", id);
        leaveTypeApprovalRulesRepository.deleteById(id);
    }
}
