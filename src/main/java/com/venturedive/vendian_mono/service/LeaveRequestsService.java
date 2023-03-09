package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveRequests;
import com.venturedive.vendian_mono.repository.LeaveRequestsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveRequests}.
 */
@Service
@Transactional
public class LeaveRequestsService {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestsService.class);

    private final LeaveRequestsRepository leaveRequestsRepository;

    public LeaveRequestsService(LeaveRequestsRepository leaveRequestsRepository) {
        this.leaveRequestsRepository = leaveRequestsRepository;
    }

    /**
     * Save a leaveRequests.
     *
     * @param leaveRequests the entity to save.
     * @return the persisted entity.
     */
    public LeaveRequests save(LeaveRequests leaveRequests) {
        log.debug("Request to save LeaveRequests : {}", leaveRequests);
        return leaveRequestsRepository.save(leaveRequests);
    }

    /**
     * Update a leaveRequests.
     *
     * @param leaveRequests the entity to save.
     * @return the persisted entity.
     */
    public LeaveRequests update(LeaveRequests leaveRequests) {
        log.debug("Request to update LeaveRequests : {}", leaveRequests);
        return leaveRequestsRepository.save(leaveRequests);
    }

    /**
     * Partially update a leaveRequests.
     *
     * @param leaveRequests the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveRequests> partialUpdate(LeaveRequests leaveRequests) {
        log.debug("Request to partially update LeaveRequests : {}", leaveRequests);

        return leaveRequestsRepository
            .findById(leaveRequests.getId())
            .map(existingLeaveRequests -> {
                if (leaveRequests.getCreatedAt() != null) {
                    existingLeaveRequests.setCreatedAt(leaveRequests.getCreatedAt());
                }
                if (leaveRequests.getRequestStartDate() != null) {
                    existingLeaveRequests.setRequestStartDate(leaveRequests.getRequestStartDate());
                }
                if (leaveRequests.getRequestEndDate() != null) {
                    existingLeaveRequests.setRequestEndDate(leaveRequests.getRequestEndDate());
                }
                if (leaveRequests.getIsHalfDay() != null) {
                    existingLeaveRequests.setIsHalfDay(leaveRequests.getIsHalfDay());
                }
                if (leaveRequests.getStatusDate() != null) {
                    existingLeaveRequests.setStatusDate(leaveRequests.getStatusDate());
                }
                if (leaveRequests.getComments() != null) {
                    existingLeaveRequests.setComments(leaveRequests.getComments());
                }
                if (leaveRequests.getUpdatedAt() != null) {
                    existingLeaveRequests.setUpdatedAt(leaveRequests.getUpdatedAt());
                }
                if (leaveRequests.getDeletedAt() != null) {
                    existingLeaveRequests.setDeletedAt(leaveRequests.getDeletedAt());
                }
                if (leaveRequests.getVersion() != null) {
                    existingLeaveRequests.setVersion(leaveRequests.getVersion());
                }

                return existingLeaveRequests;
            })
            .map(leaveRequestsRepository::save);
    }

    /**
     * Get all the leaveRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveRequests> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveRequests");
        return leaveRequestsRepository.findAll(pageable);
    }

    /**
     * Get one leaveRequests by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveRequests> findOne(Long id) {
        log.debug("Request to get LeaveRequests : {}", id);
        return leaveRequestsRepository.findById(id);
    }

    /**
     * Delete the leaveRequests by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveRequests : {}", id);
        leaveRequestsRepository.deleteById(id);
    }
}
