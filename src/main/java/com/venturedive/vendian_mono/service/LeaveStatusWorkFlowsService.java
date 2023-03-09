package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveStatusWorkFlows;
import com.venturedive.vendian_mono.repository.LeaveStatusWorkFlowsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveStatusWorkFlows}.
 */
@Service
@Transactional
public class LeaveStatusWorkFlowsService {

    private final Logger log = LoggerFactory.getLogger(LeaveStatusWorkFlowsService.class);

    private final LeaveStatusWorkFlowsRepository leaveStatusWorkFlowsRepository;

    public LeaveStatusWorkFlowsService(LeaveStatusWorkFlowsRepository leaveStatusWorkFlowsRepository) {
        this.leaveStatusWorkFlowsRepository = leaveStatusWorkFlowsRepository;
    }

    /**
     * Save a leaveStatusWorkFlows.
     *
     * @param leaveStatusWorkFlows the entity to save.
     * @return the persisted entity.
     */
    public LeaveStatusWorkFlows save(LeaveStatusWorkFlows leaveStatusWorkFlows) {
        log.debug("Request to save LeaveStatusWorkFlows : {}", leaveStatusWorkFlows);
        return leaveStatusWorkFlowsRepository.save(leaveStatusWorkFlows);
    }

    /**
     * Update a leaveStatusWorkFlows.
     *
     * @param leaveStatusWorkFlows the entity to save.
     * @return the persisted entity.
     */
    public LeaveStatusWorkFlows update(LeaveStatusWorkFlows leaveStatusWorkFlows) {
        log.debug("Request to update LeaveStatusWorkFlows : {}", leaveStatusWorkFlows);
        return leaveStatusWorkFlowsRepository.save(leaveStatusWorkFlows);
    }

    /**
     * Partially update a leaveStatusWorkFlows.
     *
     * @param leaveStatusWorkFlows the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveStatusWorkFlows> partialUpdate(LeaveStatusWorkFlows leaveStatusWorkFlows) {
        log.debug("Request to partially update LeaveStatusWorkFlows : {}", leaveStatusWorkFlows);

        return leaveStatusWorkFlowsRepository
            .findById(leaveStatusWorkFlows.getId())
            .map(existingLeaveStatusWorkFlows -> {
                if (leaveStatusWorkFlows.getIfApprovals() != null) {
                    existingLeaveStatusWorkFlows.setIfApprovals(leaveStatusWorkFlows.getIfApprovals());
                }
                if (leaveStatusWorkFlows.getApprovalRequired() != null) {
                    existingLeaveStatusWorkFlows.setApprovalRequired(leaveStatusWorkFlows.getApprovalRequired());
                }
                if (leaveStatusWorkFlows.getCreatedAt() != null) {
                    existingLeaveStatusWorkFlows.setCreatedAt(leaveStatusWorkFlows.getCreatedAt());
                }
                if (leaveStatusWorkFlows.getUpdatedAt() != null) {
                    existingLeaveStatusWorkFlows.setUpdatedAt(leaveStatusWorkFlows.getUpdatedAt());
                }
                if (leaveStatusWorkFlows.getDeletedAt() != null) {
                    existingLeaveStatusWorkFlows.setDeletedAt(leaveStatusWorkFlows.getDeletedAt());
                }
                if (leaveStatusWorkFlows.getVersion() != null) {
                    existingLeaveStatusWorkFlows.setVersion(leaveStatusWorkFlows.getVersion());
                }

                return existingLeaveStatusWorkFlows;
            })
            .map(leaveStatusWorkFlowsRepository::save);
    }

    /**
     * Get all the leaveStatusWorkFlows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveStatusWorkFlows> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveStatusWorkFlows");
        return leaveStatusWorkFlowsRepository.findAll(pageable);
    }

    /**
     * Get one leaveStatusWorkFlows by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveStatusWorkFlows> findOne(Long id) {
        log.debug("Request to get LeaveStatusWorkFlows : {}", id);
        return leaveStatusWorkFlowsRepository.findById(id);
    }

    /**
     * Delete the leaveStatusWorkFlows by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveStatusWorkFlows : {}", id);
        leaveStatusWorkFlowsRepository.deleteById(id);
    }
}
