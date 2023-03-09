package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveEscalationApprovers;
import com.venturedive.vendian_mono.repository.LeaveEscalationApproversRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveEscalationApprovers}.
 */
@Service
@Transactional
public class LeaveEscalationApproversService {

    private final Logger log = LoggerFactory.getLogger(LeaveEscalationApproversService.class);

    private final LeaveEscalationApproversRepository leaveEscalationApproversRepository;

    public LeaveEscalationApproversService(LeaveEscalationApproversRepository leaveEscalationApproversRepository) {
        this.leaveEscalationApproversRepository = leaveEscalationApproversRepository;
    }

    /**
     * Save a leaveEscalationApprovers.
     *
     * @param leaveEscalationApprovers the entity to save.
     * @return the persisted entity.
     */
    public LeaveEscalationApprovers save(LeaveEscalationApprovers leaveEscalationApprovers) {
        log.debug("Request to save LeaveEscalationApprovers : {}", leaveEscalationApprovers);
        return leaveEscalationApproversRepository.save(leaveEscalationApprovers);
    }

    /**
     * Update a leaveEscalationApprovers.
     *
     * @param leaveEscalationApprovers the entity to save.
     * @return the persisted entity.
     */
    public LeaveEscalationApprovers update(LeaveEscalationApprovers leaveEscalationApprovers) {
        log.debug("Request to update LeaveEscalationApprovers : {}", leaveEscalationApprovers);
        return leaveEscalationApproversRepository.save(leaveEscalationApprovers);
    }

    /**
     * Partially update a leaveEscalationApprovers.
     *
     * @param leaveEscalationApprovers the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveEscalationApprovers> partialUpdate(LeaveEscalationApprovers leaveEscalationApprovers) {
        log.debug("Request to partially update LeaveEscalationApprovers : {}", leaveEscalationApprovers);

        return leaveEscalationApproversRepository
            .findById(leaveEscalationApprovers.getId())
            .map(existingLeaveEscalationApprovers -> {
                if (leaveEscalationApprovers.getSource() != null) {
                    existingLeaveEscalationApprovers.setSource(leaveEscalationApprovers.getSource());
                }
                if (leaveEscalationApprovers.getMinApprovals() != null) {
                    existingLeaveEscalationApprovers.setMinApprovals(leaveEscalationApprovers.getMinApprovals());
                }
                if (leaveEscalationApprovers.getPriority() != null) {
                    existingLeaveEscalationApprovers.setPriority(leaveEscalationApprovers.getPriority());
                }
                if (leaveEscalationApprovers.getEffDate() != null) {
                    existingLeaveEscalationApprovers.setEffDate(leaveEscalationApprovers.getEffDate());
                }
                if (leaveEscalationApprovers.getCreatedAt() != null) {
                    existingLeaveEscalationApprovers.setCreatedAt(leaveEscalationApprovers.getCreatedAt());
                }
                if (leaveEscalationApprovers.getUpdatedAt() != null) {
                    existingLeaveEscalationApprovers.setUpdatedAt(leaveEscalationApprovers.getUpdatedAt());
                }
                if (leaveEscalationApprovers.getEndDate() != null) {
                    existingLeaveEscalationApprovers.setEndDate(leaveEscalationApprovers.getEndDate());
                }
                if (leaveEscalationApprovers.getVersion() != null) {
                    existingLeaveEscalationApprovers.setVersion(leaveEscalationApprovers.getVersion());
                }

                return existingLeaveEscalationApprovers;
            })
            .map(leaveEscalationApproversRepository::save);
    }

    /**
     * Get all the leaveEscalationApprovers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveEscalationApprovers> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveEscalationApprovers");
        return leaveEscalationApproversRepository.findAll(pageable);
    }

    /**
     * Get one leaveEscalationApprovers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveEscalationApprovers> findOne(Long id) {
        log.debug("Request to get LeaveEscalationApprovers : {}", id);
        return leaveEscalationApproversRepository.findById(id);
    }

    /**
     * Delete the leaveEscalationApprovers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveEscalationApprovers : {}", id);
        leaveEscalationApproversRepository.deleteById(id);
    }
}
