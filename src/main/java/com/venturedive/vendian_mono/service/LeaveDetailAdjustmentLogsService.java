package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveDetailAdjustmentLogs;
import com.venturedive.vendian_mono.repository.LeaveDetailAdjustmentLogsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveDetailAdjustmentLogs}.
 */
@Service
@Transactional
public class LeaveDetailAdjustmentLogsService {

    private final Logger log = LoggerFactory.getLogger(LeaveDetailAdjustmentLogsService.class);

    private final LeaveDetailAdjustmentLogsRepository leaveDetailAdjustmentLogsRepository;

    public LeaveDetailAdjustmentLogsService(LeaveDetailAdjustmentLogsRepository leaveDetailAdjustmentLogsRepository) {
        this.leaveDetailAdjustmentLogsRepository = leaveDetailAdjustmentLogsRepository;
    }

    /**
     * Save a leaveDetailAdjustmentLogs.
     *
     * @param leaveDetailAdjustmentLogs the entity to save.
     * @return the persisted entity.
     */
    public LeaveDetailAdjustmentLogs save(LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs) {
        log.debug("Request to save LeaveDetailAdjustmentLogs : {}", leaveDetailAdjustmentLogs);
        return leaveDetailAdjustmentLogsRepository.save(leaveDetailAdjustmentLogs);
    }

    /**
     * Update a leaveDetailAdjustmentLogs.
     *
     * @param leaveDetailAdjustmentLogs the entity to save.
     * @return the persisted entity.
     */
    public LeaveDetailAdjustmentLogs update(LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs) {
        log.debug("Request to update LeaveDetailAdjustmentLogs : {}", leaveDetailAdjustmentLogs);
        return leaveDetailAdjustmentLogsRepository.save(leaveDetailAdjustmentLogs);
    }

    /**
     * Partially update a leaveDetailAdjustmentLogs.
     *
     * @param leaveDetailAdjustmentLogs the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveDetailAdjustmentLogs> partialUpdate(LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs) {
        log.debug("Request to partially update LeaveDetailAdjustmentLogs : {}", leaveDetailAdjustmentLogs);

        return leaveDetailAdjustmentLogsRepository
            .findById(leaveDetailAdjustmentLogs.getId())
            .map(existingLeaveDetailAdjustmentLogs -> {
                if (leaveDetailAdjustmentLogs.getAction() != null) {
                    existingLeaveDetailAdjustmentLogs.setAction(leaveDetailAdjustmentLogs.getAction());
                }
                if (leaveDetailAdjustmentLogs.getCount() != null) {
                    existingLeaveDetailAdjustmentLogs.setCount(leaveDetailAdjustmentLogs.getCount());
                }
                if (leaveDetailAdjustmentLogs.getCreatedAt() != null) {
                    existingLeaveDetailAdjustmentLogs.setCreatedAt(leaveDetailAdjustmentLogs.getCreatedAt());
                }
                if (leaveDetailAdjustmentLogs.getUpdatedAt() != null) {
                    existingLeaveDetailAdjustmentLogs.setUpdatedAt(leaveDetailAdjustmentLogs.getUpdatedAt());
                }
                if (leaveDetailAdjustmentLogs.getVersion() != null) {
                    existingLeaveDetailAdjustmentLogs.setVersion(leaveDetailAdjustmentLogs.getVersion());
                }
                if (leaveDetailAdjustmentLogs.getQuotaBeforeAdjustment() != null) {
                    existingLeaveDetailAdjustmentLogs.setQuotaBeforeAdjustment(leaveDetailAdjustmentLogs.getQuotaBeforeAdjustment());
                }
                if (leaveDetailAdjustmentLogs.getQuotaAfterAdjustment() != null) {
                    existingLeaveDetailAdjustmentLogs.setQuotaAfterAdjustment(leaveDetailAdjustmentLogs.getQuotaAfterAdjustment());
                }
                if (leaveDetailAdjustmentLogs.getComment() != null) {
                    existingLeaveDetailAdjustmentLogs.setComment(leaveDetailAdjustmentLogs.getComment());
                }

                return existingLeaveDetailAdjustmentLogs;
            })
            .map(leaveDetailAdjustmentLogsRepository::save);
    }

    /**
     * Get all the leaveDetailAdjustmentLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveDetailAdjustmentLogs> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveDetailAdjustmentLogs");
        return leaveDetailAdjustmentLogsRepository.findAll(pageable);
    }

    /**
     * Get one leaveDetailAdjustmentLogs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveDetailAdjustmentLogs> findOne(Long id) {
        log.debug("Request to get LeaveDetailAdjustmentLogs : {}", id);
        return leaveDetailAdjustmentLogsRepository.findById(id);
    }

    /**
     * Delete the leaveDetailAdjustmentLogs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveDetailAdjustmentLogs : {}", id);
        leaveDetailAdjustmentLogsRepository.deleteById(id);
    }
}
