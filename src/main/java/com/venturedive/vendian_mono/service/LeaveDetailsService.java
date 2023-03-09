package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.LeaveDetails;
import com.venturedive.vendian_mono.repository.LeaveDetailsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeaveDetails}.
 */
@Service
@Transactional
public class LeaveDetailsService {

    private final Logger log = LoggerFactory.getLogger(LeaveDetailsService.class);

    private final LeaveDetailsRepository leaveDetailsRepository;

    public LeaveDetailsService(LeaveDetailsRepository leaveDetailsRepository) {
        this.leaveDetailsRepository = leaveDetailsRepository;
    }

    /**
     * Save a leaveDetails.
     *
     * @param leaveDetails the entity to save.
     * @return the persisted entity.
     */
    public LeaveDetails save(LeaveDetails leaveDetails) {
        log.debug("Request to save LeaveDetails : {}", leaveDetails);
        return leaveDetailsRepository.save(leaveDetails);
    }

    /**
     * Update a leaveDetails.
     *
     * @param leaveDetails the entity to save.
     * @return the persisted entity.
     */
    public LeaveDetails update(LeaveDetails leaveDetails) {
        log.debug("Request to update LeaveDetails : {}", leaveDetails);
        return leaveDetailsRepository.save(leaveDetails);
    }

    /**
     * Partially update a leaveDetails.
     *
     * @param leaveDetails the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeaveDetails> partialUpdate(LeaveDetails leaveDetails) {
        log.debug("Request to partially update LeaveDetails : {}", leaveDetails);

        return leaveDetailsRepository
            .findById(leaveDetails.getId())
            .map(existingLeaveDetails -> {
                if (leaveDetails.getTotal() != null) {
                    existingLeaveDetails.setTotal(leaveDetails.getTotal());
                }
                if (leaveDetails.getUsed() != null) {
                    existingLeaveDetails.setUsed(leaveDetails.getUsed());
                }
                if (leaveDetails.getEffDate() != null) {
                    existingLeaveDetails.setEffDate(leaveDetails.getEffDate());
                }
                if (leaveDetails.getCreatedAt() != null) {
                    existingLeaveDetails.setCreatedAt(leaveDetails.getCreatedAt());
                }
                if (leaveDetails.getUpdatedAt() != null) {
                    existingLeaveDetails.setUpdatedAt(leaveDetails.getUpdatedAt());
                }
                if (leaveDetails.getEndDate() != null) {
                    existingLeaveDetails.setEndDate(leaveDetails.getEndDate());
                }
                if (leaveDetails.getVersion() != null) {
                    existingLeaveDetails.setVersion(leaveDetails.getVersion());
                }

                return existingLeaveDetails;
            })
            .map(leaveDetailsRepository::save);
    }

    /**
     * Get all the leaveDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveDetails> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveDetails");
        return leaveDetailsRepository.findAll(pageable);
    }

    /**
     * Get one leaveDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveDetails> findOne(Long id) {
        log.debug("Request to get LeaveDetails : {}", id);
        return leaveDetailsRepository.findById(id);
    }

    /**
     * Delete the leaveDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveDetails : {}", id);
        leaveDetailsRepository.deleteById(id);
    }
}
