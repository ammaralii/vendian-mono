package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ApproverGroups;
import com.venturedive.vendian_mono.repository.ApproverGroupsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApproverGroups}.
 */
@Service
@Transactional
public class ApproverGroupsService {

    private final Logger log = LoggerFactory.getLogger(ApproverGroupsService.class);

    private final ApproverGroupsRepository approverGroupsRepository;

    public ApproverGroupsService(ApproverGroupsRepository approverGroupsRepository) {
        this.approverGroupsRepository = approverGroupsRepository;
    }

    /**
     * Save a approverGroups.
     *
     * @param approverGroups the entity to save.
     * @return the persisted entity.
     */
    public ApproverGroups save(ApproverGroups approverGroups) {
        log.debug("Request to save ApproverGroups : {}", approverGroups);
        return approverGroupsRepository.save(approverGroups);
    }

    /**
     * Update a approverGroups.
     *
     * @param approverGroups the entity to save.
     * @return the persisted entity.
     */
    public ApproverGroups update(ApproverGroups approverGroups) {
        log.debug("Request to update ApproverGroups : {}", approverGroups);
        return approverGroupsRepository.save(approverGroups);
    }

    /**
     * Partially update a approverGroups.
     *
     * @param approverGroups the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApproverGroups> partialUpdate(ApproverGroups approverGroups) {
        log.debug("Request to partially update ApproverGroups : {}", approverGroups);

        return approverGroupsRepository
            .findById(approverGroups.getId())
            .map(existingApproverGroups -> {
                if (approverGroups.getReferenceId() != null) {
                    existingApproverGroups.setReferenceId(approverGroups.getReferenceId());
                }
                if (approverGroups.getReferenceType() != null) {
                    existingApproverGroups.setReferenceType(approverGroups.getReferenceType());
                }
                if (approverGroups.getCreatedAt() != null) {
                    existingApproverGroups.setCreatedAt(approverGroups.getCreatedAt());
                }
                if (approverGroups.getUpdatedAt() != null) {
                    existingApproverGroups.setUpdatedAt(approverGroups.getUpdatedAt());
                }
                if (approverGroups.getDeletedAt() != null) {
                    existingApproverGroups.setDeletedAt(approverGroups.getDeletedAt());
                }
                if (approverGroups.getVersion() != null) {
                    existingApproverGroups.setVersion(approverGroups.getVersion());
                }

                return existingApproverGroups;
            })
            .map(approverGroupsRepository::save);
    }

    /**
     * Get all the approverGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApproverGroups> findAll(Pageable pageable) {
        log.debug("Request to get all ApproverGroups");
        return approverGroupsRepository.findAll(pageable);
    }

    /**
     * Get one approverGroups by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApproverGroups> findOne(Long id) {
        log.debug("Request to get ApproverGroups : {}", id);
        return approverGroupsRepository.findById(id);
    }

    /**
     * Delete the approverGroups by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApproverGroups : {}", id);
        approverGroupsRepository.deleteById(id);
    }
}
