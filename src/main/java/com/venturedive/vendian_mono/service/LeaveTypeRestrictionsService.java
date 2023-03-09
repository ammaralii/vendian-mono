package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveTypeRestrictions;
import com.venturedive.vendian_mono.repository.LeaveTypeRestrictionsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveTypeRestrictions}.
 */
@Service
@Transactional
public class LeaveTypeRestrictionsService {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeRestrictionsService.class);

    private final LeaveTypeRestrictionsRepository leaveTypeRestrictionsRepository;

    public LeaveTypeRestrictionsService(LeaveTypeRestrictionsRepository leaveTypeRestrictionsRepository) {
        this.leaveTypeRestrictionsRepository = leaveTypeRestrictionsRepository;
    }

    /**
     * Save a leaveTypeRestrictions.
     *
     * @param leaveTypeRestrictions the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypeRestrictions save(LeaveTypeRestrictions leaveTypeRestrictions) {
        log.debug("Request to save LeaveTypeRestrictions : {}", leaveTypeRestrictions);
        return leaveTypeRestrictionsRepository.save(leaveTypeRestrictions);
    }

    /**
     * Update a leaveTypeRestrictions.
     *
     * @param leaveTypeRestrictions the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypeRestrictions update(LeaveTypeRestrictions leaveTypeRestrictions) {
        log.debug("Request to update LeaveTypeRestrictions : {}", leaveTypeRestrictions);
        return leaveTypeRestrictionsRepository.save(leaveTypeRestrictions);
    }

    /**
     * Partially update a leaveTypeRestrictions.
     *
     * @param leaveTypeRestrictions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveTypeRestrictions> partialUpdate(LeaveTypeRestrictions leaveTypeRestrictions) {
        log.debug("Request to partially update LeaveTypeRestrictions : {}", leaveTypeRestrictions);

        return leaveTypeRestrictionsRepository
            .findById(leaveTypeRestrictions.getId())
            .map(existingLeaveTypeRestrictions -> {
                if (leaveTypeRestrictions.getRestrictionJson() != null) {
                    existingLeaveTypeRestrictions.setRestrictionJson(leaveTypeRestrictions.getRestrictionJson());
                }
                if (leaveTypeRestrictions.getEffDate() != null) {
                    existingLeaveTypeRestrictions.setEffDate(leaveTypeRestrictions.getEffDate());
                }
                if (leaveTypeRestrictions.getCreatedAt() != null) {
                    existingLeaveTypeRestrictions.setCreatedAt(leaveTypeRestrictions.getCreatedAt());
                }
                if (leaveTypeRestrictions.getUpdatedAt() != null) {
                    existingLeaveTypeRestrictions.setUpdatedAt(leaveTypeRestrictions.getUpdatedAt());
                }
                if (leaveTypeRestrictions.getEndDate() != null) {
                    existingLeaveTypeRestrictions.setEndDate(leaveTypeRestrictions.getEndDate());
                }
                if (leaveTypeRestrictions.getVersion() != null) {
                    existingLeaveTypeRestrictions.setVersion(leaveTypeRestrictions.getVersion());
                }

                return existingLeaveTypeRestrictions;
            })
            .map(leaveTypeRestrictionsRepository::save);
    }

    /**
     * Get all the leaveTypeRestrictions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypeRestrictions> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveTypeRestrictions");
        return leaveTypeRestrictionsRepository.findAll(pageable);
    }

    /**
     * Get one leaveTypeRestrictions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveTypeRestrictions> findOne(Long id) {
        log.debug("Request to get LeaveTypeRestrictions : {}", id);
        return leaveTypeRestrictionsRepository.findById(id);
    }

    /**
     * Delete the leaveTypeRestrictions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveTypeRestrictions : {}", id);
        leaveTypeRestrictionsRepository.deleteById(id);
    }
}
