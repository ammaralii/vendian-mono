package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveEscalationCriterias;
import com.venturedive.vendian_mono.repository.LeaveEscalationCriteriasRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveEscalationCriterias}.
 */
@Service
@Transactional
public class LeaveEscalationCriteriasService {

    private final Logger log = LoggerFactory.getLogger(LeaveEscalationCriteriasService.class);

    private final LeaveEscalationCriteriasRepository leaveEscalationCriteriasRepository;

    public LeaveEscalationCriteriasService(LeaveEscalationCriteriasRepository leaveEscalationCriteriasRepository) {
        this.leaveEscalationCriteriasRepository = leaveEscalationCriteriasRepository;
    }

    /**
     * Save a leaveEscalationCriterias.
     *
     * @param leaveEscalationCriterias the entity to save.
     * @return the persisted entity.
     */
    public LeaveEscalationCriterias save(LeaveEscalationCriterias leaveEscalationCriterias) {
        log.debug("Request to save LeaveEscalationCriterias : {}", leaveEscalationCriterias);
        return leaveEscalationCriteriasRepository.save(leaveEscalationCriterias);
    }

    /**
     * Update a leaveEscalationCriterias.
     *
     * @param leaveEscalationCriterias the entity to save.
     * @return the persisted entity.
     */
    public LeaveEscalationCriterias update(LeaveEscalationCriterias leaveEscalationCriterias) {
        log.debug("Request to update LeaveEscalationCriterias : {}", leaveEscalationCriterias);
        return leaveEscalationCriteriasRepository.save(leaveEscalationCriterias);
    }

    /**
     * Partially update a leaveEscalationCriterias.
     *
     * @param leaveEscalationCriterias the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveEscalationCriterias> partialUpdate(LeaveEscalationCriterias leaveEscalationCriterias) {
        log.debug("Request to partially update LeaveEscalationCriterias : {}", leaveEscalationCriterias);

        return leaveEscalationCriteriasRepository
            .findById(leaveEscalationCriterias.getId())
            .map(existingLeaveEscalationCriterias -> {
                if (leaveEscalationCriterias.getName() != null) {
                    existingLeaveEscalationCriterias.setName(leaveEscalationCriterias.getName());
                }
                if (leaveEscalationCriterias.getPriority() != null) {
                    existingLeaveEscalationCriterias.setPriority(leaveEscalationCriterias.getPriority());
                }
                if (leaveEscalationCriterias.getTotal() != null) {
                    existingLeaveEscalationCriterias.setTotal(leaveEscalationCriterias.getTotal());
                }
                if (leaveEscalationCriterias.getEffDate() != null) {
                    existingLeaveEscalationCriterias.setEffDate(leaveEscalationCriterias.getEffDate());
                }
                if (leaveEscalationCriterias.getCreatedAt() != null) {
                    existingLeaveEscalationCriterias.setCreatedAt(leaveEscalationCriterias.getCreatedAt());
                }
                if (leaveEscalationCriterias.getUpdatedAt() != null) {
                    existingLeaveEscalationCriterias.setUpdatedAt(leaveEscalationCriterias.getUpdatedAt());
                }
                if (leaveEscalationCriterias.getEndDate() != null) {
                    existingLeaveEscalationCriterias.setEndDate(leaveEscalationCriterias.getEndDate());
                }
                if (leaveEscalationCriterias.getVersion() != null) {
                    existingLeaveEscalationCriterias.setVersion(leaveEscalationCriterias.getVersion());
                }

                return existingLeaveEscalationCriterias;
            })
            .map(leaveEscalationCriteriasRepository::save);
    }

    /**
     * Get all the leaveEscalationCriterias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveEscalationCriterias> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveEscalationCriterias");
        return leaveEscalationCriteriasRepository.findAll(pageable);
    }

    /**
     * Get one leaveEscalationCriterias by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveEscalationCriterias> findOne(Long id) {
        log.debug("Request to get LeaveEscalationCriterias : {}", id);
        return leaveEscalationCriteriasRepository.findById(id);
    }

    /**
     * Delete the leaveEscalationCriterias by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveEscalationCriterias : {}", id);
        leaveEscalationCriteriasRepository.deleteById(id);
    }
}
