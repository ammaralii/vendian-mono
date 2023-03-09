package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveTypeConfigurations;
import com.venturedive.vendian_mono.repository.LeaveTypeConfigurationsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveTypeConfigurations}.
 */
@Service
@Transactional
public class LeaveTypeConfigurationsService {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeConfigurationsService.class);

    private final LeaveTypeConfigurationsRepository leaveTypeConfigurationsRepository;

    public LeaveTypeConfigurationsService(LeaveTypeConfigurationsRepository leaveTypeConfigurationsRepository) {
        this.leaveTypeConfigurationsRepository = leaveTypeConfigurationsRepository;
    }

    /**
     * Save a leaveTypeConfigurations.
     *
     * @param leaveTypeConfigurations the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypeConfigurations save(LeaveTypeConfigurations leaveTypeConfigurations) {
        log.debug("Request to save LeaveTypeConfigurations : {}", leaveTypeConfigurations);
        return leaveTypeConfigurationsRepository.save(leaveTypeConfigurations);
    }

    /**
     * Update a leaveTypeConfigurations.
     *
     * @param leaveTypeConfigurations the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypeConfigurations update(LeaveTypeConfigurations leaveTypeConfigurations) {
        log.debug("Request to update LeaveTypeConfigurations : {}", leaveTypeConfigurations);
        return leaveTypeConfigurationsRepository.save(leaveTypeConfigurations);
    }

    /**
     * Partially update a leaveTypeConfigurations.
     *
     * @param leaveTypeConfigurations the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveTypeConfigurations> partialUpdate(LeaveTypeConfigurations leaveTypeConfigurations) {
        log.debug("Request to partially update LeaveTypeConfigurations : {}", leaveTypeConfigurations);

        return leaveTypeConfigurationsRepository
            .findById(leaveTypeConfigurations.getId())
            .map(existingLeaveTypeConfigurations -> {
                if (leaveTypeConfigurations.getNoOfLeaves() != null) {
                    existingLeaveTypeConfigurations.setNoOfLeaves(leaveTypeConfigurations.getNoOfLeaves());
                }
                if (leaveTypeConfigurations.getTenureCycle() != null) {
                    existingLeaveTypeConfigurations.setTenureCycle(leaveTypeConfigurations.getTenureCycle());
                }
                if (leaveTypeConfigurations.getTo() != null) {
                    existingLeaveTypeConfigurations.setTo(leaveTypeConfigurations.getTo());
                }
                if (leaveTypeConfigurations.getFrom() != null) {
                    existingLeaveTypeConfigurations.setFrom(leaveTypeConfigurations.getFrom());
                }
                if (leaveTypeConfigurations.getInclusivity() != null) {
                    existingLeaveTypeConfigurations.setInclusivity(leaveTypeConfigurations.getInclusivity());
                }
                if (leaveTypeConfigurations.getMaxQuota() != null) {
                    existingLeaveTypeConfigurations.setMaxQuota(leaveTypeConfigurations.getMaxQuota());
                }
                if (leaveTypeConfigurations.getIsAccured() != null) {
                    existingLeaveTypeConfigurations.setIsAccured(leaveTypeConfigurations.getIsAccured());
                }
                if (leaveTypeConfigurations.getEffDate() != null) {
                    existingLeaveTypeConfigurations.setEffDate(leaveTypeConfigurations.getEffDate());
                }
                if (leaveTypeConfigurations.getCreatedAt() != null) {
                    existingLeaveTypeConfigurations.setCreatedAt(leaveTypeConfigurations.getCreatedAt());
                }
                if (leaveTypeConfigurations.getUpdatedAt() != null) {
                    existingLeaveTypeConfigurations.setUpdatedAt(leaveTypeConfigurations.getUpdatedAt());
                }
                if (leaveTypeConfigurations.getEndDate() != null) {
                    existingLeaveTypeConfigurations.setEndDate(leaveTypeConfigurations.getEndDate());
                }
                if (leaveTypeConfigurations.getVersion() != null) {
                    existingLeaveTypeConfigurations.setVersion(leaveTypeConfigurations.getVersion());
                }

                return existingLeaveTypeConfigurations;
            })
            .map(leaveTypeConfigurationsRepository::save);
    }

    /**
     * Get all the leaveTypeConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypeConfigurations> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveTypeConfigurations");
        return leaveTypeConfigurationsRepository.findAll(pageable);
    }

    /**
     * Get one leaveTypeConfigurations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveTypeConfigurations> findOne(Long id) {
        log.debug("Request to get LeaveTypeConfigurations : {}", id);
        return leaveTypeConfigurationsRepository.findById(id);
    }

    /**
     * Delete the leaveTypeConfigurations by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveTypeConfigurations : {}", id);
        leaveTypeConfigurationsRepository.deleteById(id);
    }
}
