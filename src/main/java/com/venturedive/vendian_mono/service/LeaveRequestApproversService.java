package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveRequestApprovers;
import com.venturedive.vendian_mono.repository.LeaveRequestApproversRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveRequestApprovers}.
 */
@Service
@Transactional
public class LeaveRequestApproversService {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestApproversService.class);

    private final LeaveRequestApproversRepository leaveRequestApproversRepository;

    public LeaveRequestApproversService(LeaveRequestApproversRepository leaveRequestApproversRepository) {
        this.leaveRequestApproversRepository = leaveRequestApproversRepository;
    }

    /**
     * Save a leaveRequestApprovers.
     *
     * @param leaveRequestApprovers the entity to save.
     * @return the persisted entity.
     */
    public LeaveRequestApprovers save(LeaveRequestApprovers leaveRequestApprovers) {
        log.debug("Request to save LeaveRequestApprovers : {}", leaveRequestApprovers);
        return leaveRequestApproversRepository.save(leaveRequestApprovers);
    }

    /**
     * Update a leaveRequestApprovers.
     *
     * @param leaveRequestApprovers the entity to save.
     * @return the persisted entity.
     */
    public LeaveRequestApprovers update(LeaveRequestApprovers leaveRequestApprovers) {
        log.debug("Request to update LeaveRequestApprovers : {}", leaveRequestApprovers);
        return leaveRequestApproversRepository.save(leaveRequestApprovers);
    }

    /**
     * Partially update a leaveRequestApprovers.
     *
     * @param leaveRequestApprovers the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveRequestApprovers> partialUpdate(LeaveRequestApprovers leaveRequestApprovers) {
        log.debug("Request to partially update LeaveRequestApprovers : {}", leaveRequestApprovers);

        return leaveRequestApproversRepository
            .findById(leaveRequestApprovers.getId())
            .map(existingLeaveRequestApprovers -> {
                if (leaveRequestApprovers.getReference() != null) {
                    existingLeaveRequestApprovers.setReference(leaveRequestApprovers.getReference());
                }
                if (leaveRequestApprovers.getAs() != null) {
                    existingLeaveRequestApprovers.setAs(leaveRequestApprovers.getAs());
                }
                if (leaveRequestApprovers.getComments() != null) {
                    existingLeaveRequestApprovers.setComments(leaveRequestApprovers.getComments());
                }
                if (leaveRequestApprovers.getApproverGroup() != null) {
                    existingLeaveRequestApprovers.setApproverGroup(leaveRequestApprovers.getApproverGroup());
                }
                if (leaveRequestApprovers.getPriority() != null) {
                    existingLeaveRequestApprovers.setPriority(leaveRequestApprovers.getPriority());
                }
                if (leaveRequestApprovers.getStatusDate() != null) {
                    existingLeaveRequestApprovers.setStatusDate(leaveRequestApprovers.getStatusDate());
                }
                if (leaveRequestApprovers.getCreatedAt() != null) {
                    existingLeaveRequestApprovers.setCreatedAt(leaveRequestApprovers.getCreatedAt());
                }
                if (leaveRequestApprovers.getUpdatedAt() != null) {
                    existingLeaveRequestApprovers.setUpdatedAt(leaveRequestApprovers.getUpdatedAt());
                }
                if (leaveRequestApprovers.getDeletedAt() != null) {
                    existingLeaveRequestApprovers.setDeletedAt(leaveRequestApprovers.getDeletedAt());
                }
                if (leaveRequestApprovers.getVersion() != null) {
                    existingLeaveRequestApprovers.setVersion(leaveRequestApprovers.getVersion());
                }

                return existingLeaveRequestApprovers;
            })
            .map(leaveRequestApproversRepository::save);
    }

    /**
     * Get all the leaveRequestApprovers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveRequestApprovers> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveRequestApprovers");
        return leaveRequestApproversRepository.findAll(pageable);
    }

    /**
     * Get one leaveRequestApprovers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveRequestApprovers> findOne(Long id) {
        log.debug("Request to get LeaveRequestApprovers : {}", id);
        return leaveRequestApproversRepository.findById(id);
    }

    /**
     * Delete the leaveRequestApprovers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveRequestApprovers : {}", id);
        leaveRequestApproversRepository.deleteById(id);
    }
}
