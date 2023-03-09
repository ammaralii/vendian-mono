package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveRequestApproverFlows;
import com.venturedive.vendian_mono.repository.LeaveRequestApproverFlowsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveRequestApproverFlows}.
 */
@Service
@Transactional
public class LeaveRequestApproverFlowsService {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestApproverFlowsService.class);

    private final LeaveRequestApproverFlowsRepository leaveRequestApproverFlowsRepository;

    public LeaveRequestApproverFlowsService(LeaveRequestApproverFlowsRepository leaveRequestApproverFlowsRepository) {
        this.leaveRequestApproverFlowsRepository = leaveRequestApproverFlowsRepository;
    }

    /**
     * Save a leaveRequestApproverFlows.
     *
     * @param leaveRequestApproverFlows the entity to save.
     * @return the persisted entity.
     */
    public LeaveRequestApproverFlows save(LeaveRequestApproverFlows leaveRequestApproverFlows) {
        log.debug("Request to save LeaveRequestApproverFlows : {}", leaveRequestApproverFlows);
        return leaveRequestApproverFlowsRepository.save(leaveRequestApproverFlows);
    }

    /**
     * Update a leaveRequestApproverFlows.
     *
     * @param leaveRequestApproverFlows the entity to save.
     * @return the persisted entity.
     */
    public LeaveRequestApproverFlows update(LeaveRequestApproverFlows leaveRequestApproverFlows) {
        log.debug("Request to update LeaveRequestApproverFlows : {}", leaveRequestApproverFlows);
        return leaveRequestApproverFlowsRepository.save(leaveRequestApproverFlows);
    }

    /**
     * Partially update a leaveRequestApproverFlows.
     *
     * @param leaveRequestApproverFlows the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveRequestApproverFlows> partialUpdate(LeaveRequestApproverFlows leaveRequestApproverFlows) {
        log.debug("Request to partially update LeaveRequestApproverFlows : {}", leaveRequestApproverFlows);

        return leaveRequestApproverFlowsRepository
            .findById(leaveRequestApproverFlows.getId())
            .map(existingLeaveRequestApproverFlows -> {
                if (leaveRequestApproverFlows.getApprovals() != null) {
                    existingLeaveRequestApproverFlows.setApprovals(leaveRequestApproverFlows.getApprovals());
                }
                if (leaveRequestApproverFlows.getEffDate() != null) {
                    existingLeaveRequestApproverFlows.setEffDate(leaveRequestApproverFlows.getEffDate());
                }
                if (leaveRequestApproverFlows.getCreatedAt() != null) {
                    existingLeaveRequestApproverFlows.setCreatedAt(leaveRequestApproverFlows.getCreatedAt());
                }
                if (leaveRequestApproverFlows.getUpdatedAt() != null) {
                    existingLeaveRequestApproverFlows.setUpdatedAt(leaveRequestApproverFlows.getUpdatedAt());
                }
                if (leaveRequestApproverFlows.getEndDate() != null) {
                    existingLeaveRequestApproverFlows.setEndDate(leaveRequestApproverFlows.getEndDate());
                }
                if (leaveRequestApproverFlows.getVersion() != null) {
                    existingLeaveRequestApproverFlows.setVersion(leaveRequestApproverFlows.getVersion());
                }

                return existingLeaveRequestApproverFlows;
            })
            .map(leaveRequestApproverFlowsRepository::save);
    }

    /**
     * Get all the leaveRequestApproverFlows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveRequestApproverFlows> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveRequestApproverFlows");
        return leaveRequestApproverFlowsRepository.findAll(pageable);
    }

    /**
     * Get one leaveRequestApproverFlows by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveRequestApproverFlows> findOne(Long id) {
        log.debug("Request to get LeaveRequestApproverFlows : {}", id);
        return leaveRequestApproverFlowsRepository.findById(id);
    }

    /**
     * Delete the leaveRequestApproverFlows by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveRequestApproverFlows : {}", id);
        leaveRequestApproverFlowsRepository.deleteById(id);
    }
}
