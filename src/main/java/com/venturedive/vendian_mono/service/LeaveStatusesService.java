package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveStatuses;
import com.venturedive.vendian_mono.repository.LeaveStatusesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveStatuses}.
 */
@Service
@Transactional
public class LeaveStatusesService {

    private final Logger log = LoggerFactory.getLogger(LeaveStatusesService.class);

    private final LeaveStatusesRepository leaveStatusesRepository;

    public LeaveStatusesService(LeaveStatusesRepository leaveStatusesRepository) {
        this.leaveStatusesRepository = leaveStatusesRepository;
    }

    /**
     * Save a leaveStatuses.
     *
     * @param leaveStatuses the entity to save.
     * @return the persisted entity.
     */
    public LeaveStatuses save(LeaveStatuses leaveStatuses) {
        log.debug("Request to save LeaveStatuses : {}", leaveStatuses);
        return leaveStatusesRepository.save(leaveStatuses);
    }

    /**
     * Update a leaveStatuses.
     *
     * @param leaveStatuses the entity to save.
     * @return the persisted entity.
     */
    public LeaveStatuses update(LeaveStatuses leaveStatuses) {
        log.debug("Request to update LeaveStatuses : {}", leaveStatuses);
        return leaveStatusesRepository.save(leaveStatuses);
    }

    /**
     * Partially update a leaveStatuses.
     *
     * @param leaveStatuses the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveStatuses> partialUpdate(LeaveStatuses leaveStatuses) {
        log.debug("Request to partially update LeaveStatuses : {}", leaveStatuses);

        return leaveStatusesRepository
            .findById(leaveStatuses.getId())
            .map(existingLeaveStatuses -> {
                if (leaveStatuses.getName() != null) {
                    existingLeaveStatuses.setName(leaveStatuses.getName());
                }
                if (leaveStatuses.getLeaveGroup() != null) {
                    existingLeaveStatuses.setLeaveGroup(leaveStatuses.getLeaveGroup());
                }
                if (leaveStatuses.getSystemKey() != null) {
                    existingLeaveStatuses.setSystemKey(leaveStatuses.getSystemKey());
                }
                if (leaveStatuses.getIsDefault() != null) {
                    existingLeaveStatuses.setIsDefault(leaveStatuses.getIsDefault());
                }
                if (leaveStatuses.getEffDate() != null) {
                    existingLeaveStatuses.setEffDate(leaveStatuses.getEffDate());
                }
                if (leaveStatuses.getCreatedAt() != null) {
                    existingLeaveStatuses.setCreatedAt(leaveStatuses.getCreatedAt());
                }
                if (leaveStatuses.getUpdatedAt() != null) {
                    existingLeaveStatuses.setUpdatedAt(leaveStatuses.getUpdatedAt());
                }
                if (leaveStatuses.getEndDate() != null) {
                    existingLeaveStatuses.setEndDate(leaveStatuses.getEndDate());
                }
                if (leaveStatuses.getVersion() != null) {
                    existingLeaveStatuses.setVersion(leaveStatuses.getVersion());
                }

                return existingLeaveStatuses;
            })
            .map(leaveStatusesRepository::save);
    }

    /**
     * Get all the leaveStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveStatuses> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveStatuses");
        return leaveStatusesRepository.findAll(pageable);
    }

    /**
     * Get one leaveStatuses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveStatuses> findOne(Long id) {
        log.debug("Request to get LeaveStatuses : {}", id);
        return leaveStatusesRepository.findById(id);
    }

    /**
     * Delete the leaveStatuses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveStatuses : {}", id);
        leaveStatusesRepository.deleteById(id);
    }
}
