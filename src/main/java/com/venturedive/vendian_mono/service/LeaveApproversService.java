package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveApprovers;
import com.venturedive.vendian_mono.repository.LeaveApproversRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveApprovers}.
 */
@Service
@Transactional
public class LeaveApproversService {

    private final Logger log = LoggerFactory.getLogger(LeaveApproversService.class);

    private final LeaveApproversRepository leaveApproversRepository;

    public LeaveApproversService(LeaveApproversRepository leaveApproversRepository) {
        this.leaveApproversRepository = leaveApproversRepository;
    }

    /**
     * Save a leaveApprovers.
     *
     * @param leaveApprovers the entity to save.
     * @return the persisted entity.
     */
    public LeaveApprovers save(LeaveApprovers leaveApprovers) {
        log.debug("Request to save LeaveApprovers : {}", leaveApprovers);
        return leaveApproversRepository.save(leaveApprovers);
    }

    /**
     * Update a leaveApprovers.
     *
     * @param leaveApprovers the entity to save.
     * @return the persisted entity.
     */
    public LeaveApprovers update(LeaveApprovers leaveApprovers) {
        log.debug("Request to update LeaveApprovers : {}", leaveApprovers);
        return leaveApproversRepository.save(leaveApprovers);
    }

    /**
     * Partially update a leaveApprovers.
     *
     * @param leaveApprovers the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveApprovers> partialUpdate(LeaveApprovers leaveApprovers) {
        log.debug("Request to partially update LeaveApprovers : {}", leaveApprovers);

        return leaveApproversRepository
            .findById(leaveApprovers.getId())
            .map(existingLeaveApprovers -> {
                if (leaveApprovers.getSource() != null) {
                    existingLeaveApprovers.setSource(leaveApprovers.getSource());
                }
                if (leaveApprovers.getMinApprovals() != null) {
                    existingLeaveApprovers.setMinApprovals(leaveApprovers.getMinApprovals());
                }
                if (leaveApprovers.getPriority() != null) {
                    existingLeaveApprovers.setPriority(leaveApprovers.getPriority());
                }
                if (leaveApprovers.getEffDate() != null) {
                    existingLeaveApprovers.setEffDate(leaveApprovers.getEffDate());
                }
                if (leaveApprovers.getCreatedAt() != null) {
                    existingLeaveApprovers.setCreatedAt(leaveApprovers.getCreatedAt());
                }
                if (leaveApprovers.getUpdatedAt() != null) {
                    existingLeaveApprovers.setUpdatedAt(leaveApprovers.getUpdatedAt());
                }
                if (leaveApprovers.getEndDate() != null) {
                    existingLeaveApprovers.setEndDate(leaveApprovers.getEndDate());
                }
                if (leaveApprovers.getVersion() != null) {
                    existingLeaveApprovers.setVersion(leaveApprovers.getVersion());
                }

                return existingLeaveApprovers;
            })
            .map(leaveApproversRepository::save);
    }

    /**
     * Get all the leaveApprovers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveApprovers> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveApprovers");
        return leaveApproversRepository.findAll(pageable);
    }

    /**
     * Get one leaveApprovers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveApprovers> findOne(Long id) {
        log.debug("Request to get LeaveApprovers : {}", id);
        return leaveApproversRepository.findById(id);
    }

    /**
     * Delete the leaveApprovers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveApprovers : {}", id);
        leaveApproversRepository.deleteById(id);
    }
}
