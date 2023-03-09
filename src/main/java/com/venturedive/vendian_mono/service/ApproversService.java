package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Approvers;
import com.venturedive.vendian_mono.repository.ApproversRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Approvers}.
 */
@Service
@Transactional
public class ApproversService {

    private final Logger log = LoggerFactory.getLogger(ApproversService.class);

    private final ApproversRepository approversRepository;

    public ApproversService(ApproversRepository approversRepository) {
        this.approversRepository = approversRepository;
    }

    /**
     * Save a approvers.
     *
     * @param approvers the entity to save.
     * @return the persisted entity.
     */
    public Approvers save(Approvers approvers) {
        log.debug("Request to save Approvers : {}", approvers);
        return approversRepository.save(approvers);
    }

    /**
     * Update a approvers.
     *
     * @param approvers the entity to save.
     * @return the persisted entity.
     */
    public Approvers update(Approvers approvers) {
        log.debug("Request to update Approvers : {}", approvers);
        return approversRepository.save(approvers);
    }

    /**
     * Partially update a approvers.
     *
     * @param approvers the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Approvers> partialUpdate(Approvers approvers) {
        log.debug("Request to partially update Approvers : {}", approvers);

        return approversRepository
            .findById(approvers.getId())
            .map(existingApprovers -> {
                if (approvers.getUserId() != null) {
                    existingApprovers.setUserId(approvers.getUserId());
                }
                if (approvers.getReference() != null) {
                    existingApprovers.setReference(approvers.getReference());
                }
                if (approvers.getAs() != null) {
                    existingApprovers.setAs(approvers.getAs());
                }
                if (approvers.getComment() != null) {
                    existingApprovers.setComment(approvers.getComment());
                }
                if (approvers.getStatus() != null) {
                    existingApprovers.setStatus(approvers.getStatus());
                }
                if (approvers.getStausDate() != null) {
                    existingApprovers.setStausDate(approvers.getStausDate());
                }
                if (approvers.getPriority() != null) {
                    existingApprovers.setPriority(approvers.getPriority());
                }
                if (approvers.getCreatedAt() != null) {
                    existingApprovers.setCreatedAt(approvers.getCreatedAt());
                }
                if (approvers.getUpdatedAt() != null) {
                    existingApprovers.setUpdatedAt(approvers.getUpdatedAt());
                }
                if (approvers.getDeletedAt() != null) {
                    existingApprovers.setDeletedAt(approvers.getDeletedAt());
                }
                if (approvers.getVersion() != null) {
                    existingApprovers.setVersion(approvers.getVersion());
                }

                return existingApprovers;
            })
            .map(approversRepository::save);
    }

    /**
     * Get all the approvers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Approvers> findAll(Pageable pageable) {
        log.debug("Request to get all Approvers");
        return approversRepository.findAll(pageable);
    }

    /**
     * Get one approvers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Approvers> findOne(Long id) {
        log.debug("Request to get Approvers : {}", id);
        return approversRepository.findById(id);
    }

    /**
     * Delete the approvers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Approvers : {}", id);
        approversRepository.deleteById(id);
    }
}
