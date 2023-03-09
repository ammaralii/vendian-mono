package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveApprovalCriterias;
import com.venturedive.vendian_mono.repository.LeaveApprovalCriteriasRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveApprovalCriterias}.
 */
@Service
@Transactional
public class LeaveApprovalCriteriasService {

    private final Logger log = LoggerFactory.getLogger(LeaveApprovalCriteriasService.class);

    private final LeaveApprovalCriteriasRepository leaveApprovalCriteriasRepository;

    public LeaveApprovalCriteriasService(LeaveApprovalCriteriasRepository leaveApprovalCriteriasRepository) {
        this.leaveApprovalCriteriasRepository = leaveApprovalCriteriasRepository;
    }

    /**
     * Save a leaveApprovalCriterias.
     *
     * @param leaveApprovalCriterias the entity to save.
     * @return the persisted entity.
     */
    public LeaveApprovalCriterias save(LeaveApprovalCriterias leaveApprovalCriterias) {
        log.debug("Request to save LeaveApprovalCriterias : {}", leaveApprovalCriterias);
        return leaveApprovalCriteriasRepository.save(leaveApprovalCriterias);
    }

    /**
     * Update a leaveApprovalCriterias.
     *
     * @param leaveApprovalCriterias the entity to save.
     * @return the persisted entity.
     */
    public LeaveApprovalCriterias update(LeaveApprovalCriterias leaveApprovalCriterias) {
        log.debug("Request to update LeaveApprovalCriterias : {}", leaveApprovalCriterias);
        return leaveApprovalCriteriasRepository.save(leaveApprovalCriterias);
    }

    /**
     * Partially update a leaveApprovalCriterias.
     *
     * @param leaveApprovalCriterias the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveApprovalCriterias> partialUpdate(LeaveApprovalCriterias leaveApprovalCriterias) {
        log.debug("Request to partially update LeaveApprovalCriterias : {}", leaveApprovalCriterias);

        return leaveApprovalCriteriasRepository
            .findById(leaveApprovalCriterias.getId())
            .map(existingLeaveApprovalCriterias -> {
                if (leaveApprovalCriterias.getName() != null) {
                    existingLeaveApprovalCriterias.setName(leaveApprovalCriterias.getName());
                }
                if (leaveApprovalCriterias.getPriority() != null) {
                    existingLeaveApprovalCriterias.setPriority(leaveApprovalCriterias.getPriority());
                }
                if (leaveApprovalCriterias.getEffDate() != null) {
                    existingLeaveApprovalCriterias.setEffDate(leaveApprovalCriterias.getEffDate());
                }
                if (leaveApprovalCriterias.getCreatedAt() != null) {
                    existingLeaveApprovalCriterias.setCreatedAt(leaveApprovalCriterias.getCreatedAt());
                }
                if (leaveApprovalCriterias.getUpdatedAt() != null) {
                    existingLeaveApprovalCriterias.setUpdatedAt(leaveApprovalCriterias.getUpdatedAt());
                }
                if (leaveApprovalCriterias.getEndDate() != null) {
                    existingLeaveApprovalCriterias.setEndDate(leaveApprovalCriterias.getEndDate());
                }
                if (leaveApprovalCriterias.getVersion() != null) {
                    existingLeaveApprovalCriterias.setVersion(leaveApprovalCriterias.getVersion());
                }

                return existingLeaveApprovalCriterias;
            })
            .map(leaveApprovalCriteriasRepository::save);
    }

    /**
     * Get all the leaveApprovalCriterias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveApprovalCriterias> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveApprovalCriterias");
        return leaveApprovalCriteriasRepository.findAll(pageable);
    }

    /**
     * Get one leaveApprovalCriterias by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveApprovalCriterias> findOne(Long id) {
        log.debug("Request to get LeaveApprovalCriterias : {}", id);
        return leaveApprovalCriteriasRepository.findById(id);
    }

    /**
     * Delete the leaveApprovalCriterias by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveApprovalCriterias : {}", id);
        leaveApprovalCriteriasRepository.deleteById(id);
    }
}
