package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveTypes;
import com.venturedive.vendian_mono.repository.LeaveTypesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveTypes}.
 */
@Service
@Transactional
public class LeaveTypesService {

    private final Logger log = LoggerFactory.getLogger(LeaveTypesService.class);

    private final LeaveTypesRepository leaveTypesRepository;

    public LeaveTypesService(LeaveTypesRepository leaveTypesRepository) {
        this.leaveTypesRepository = leaveTypesRepository;
    }

    /**
     * Save a leaveTypes.
     *
     * @param leaveTypes the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypes save(LeaveTypes leaveTypes) {
        log.debug("Request to save LeaveTypes : {}", leaveTypes);
        return leaveTypesRepository.save(leaveTypes);
    }

    /**
     * Update a leaveTypes.
     *
     * @param leaveTypes the entity to save.
     * @return the persisted entity.
     */
    public LeaveTypes update(LeaveTypes leaveTypes) {
        log.debug("Request to update LeaveTypes : {}", leaveTypes);
        return leaveTypesRepository.save(leaveTypes);
    }

    /**
     * Partially update a leaveTypes.
     *
     * @param leaveTypes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveTypes> partialUpdate(LeaveTypes leaveTypes) {
        log.debug("Request to partially update LeaveTypes : {}", leaveTypes);

        return leaveTypesRepository
            .findById(leaveTypes.getId())
            .map(existingLeaveTypes -> {
                if (leaveTypes.getName() != null) {
                    existingLeaveTypes.setName(leaveTypes.getName());
                }
                if (leaveTypes.getCategory() != null) {
                    existingLeaveTypes.setCategory(leaveTypes.getCategory());
                }
                if (leaveTypes.getCycleType() != null) {
                    existingLeaveTypes.setCycleType(leaveTypes.getCycleType());
                }
                if (leaveTypes.getCycleCount() != null) {
                    existingLeaveTypes.setCycleCount(leaveTypes.getCycleCount());
                }
                if (leaveTypes.getMaxQuota() != null) {
                    existingLeaveTypes.setMaxQuota(leaveTypes.getMaxQuota());
                }
                if (leaveTypes.getEffDate() != null) {
                    existingLeaveTypes.setEffDate(leaveTypes.getEffDate());
                }
                if (leaveTypes.getCreatedAt() != null) {
                    existingLeaveTypes.setCreatedAt(leaveTypes.getCreatedAt());
                }
                if (leaveTypes.getUpdatedAt() != null) {
                    existingLeaveTypes.setUpdatedAt(leaveTypes.getUpdatedAt());
                }
                if (leaveTypes.getEndDate() != null) {
                    existingLeaveTypes.setEndDate(leaveTypes.getEndDate());
                }
                if (leaveTypes.getVersion() != null) {
                    existingLeaveTypes.setVersion(leaveTypes.getVersion());
                }

                return existingLeaveTypes;
            })
            .map(leaveTypesRepository::save);
    }

    /**
     * Get all the leaveTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypes> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveTypes");
        return leaveTypesRepository.findAll(pageable);
    }

    /**
     * Get one leaveTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveTypes> findOne(Long id) {
        log.debug("Request to get LeaveTypes : {}", id);
        return leaveTypesRepository.findById(id);
    }

    /**
     * Delete the leaveTypes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveTypes : {}", id);
        leaveTypesRepository.deleteById(id);
    }
}
