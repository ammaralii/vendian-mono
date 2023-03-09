package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ApproverFlows;
import com.venturedive.vendian_mono.repository.ApproverFlowsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApproverFlows}.
 */
@Service
@Transactional
public class ApproverFlowsService {

    private final Logger log = LoggerFactory.getLogger(ApproverFlowsService.class);

    private final ApproverFlowsRepository approverFlowsRepository;

    public ApproverFlowsService(ApproverFlowsRepository approverFlowsRepository) {
        this.approverFlowsRepository = approverFlowsRepository;
    }

    /**
     * Save a approverFlows.
     *
     * @param approverFlows the entity to save.
     * @return the persisted entity.
     */
    public ApproverFlows save(ApproverFlows approverFlows) {
        log.debug("Request to save ApproverFlows : {}", approverFlows);
        return approverFlowsRepository.save(approverFlows);
    }

    /**
     * Update a approverFlows.
     *
     * @param approverFlows the entity to save.
     * @return the persisted entity.
     */
    public ApproverFlows update(ApproverFlows approverFlows) {
        log.debug("Request to update ApproverFlows : {}", approverFlows);
        return approverFlowsRepository.save(approverFlows);
    }

    /**
     * Partially update a approverFlows.
     *
     * @param approverFlows the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApproverFlows> partialUpdate(ApproverFlows approverFlows) {
        log.debug("Request to partially update ApproverFlows : {}", approverFlows);

        return approverFlowsRepository
            .findById(approverFlows.getId())
            .map(existingApproverFlows -> {
                if (approverFlows.getReferenceType() != null) {
                    existingApproverFlows.setReferenceType(approverFlows.getReferenceType());
                }
                if (approverFlows.getApproverStatus() != null) {
                    existingApproverFlows.setApproverStatus(approverFlows.getApproverStatus());
                }
                if (approverFlows.getApproval() != null) {
                    existingApproverFlows.setApproval(approverFlows.getApproval());
                }
                if (approverFlows.getCurrentStatus() != null) {
                    existingApproverFlows.setCurrentStatus(approverFlows.getCurrentStatus());
                }
                if (approverFlows.getNextStatus() != null) {
                    existingApproverFlows.setNextStatus(approverFlows.getNextStatus());
                }
                if (approverFlows.getEffDate() != null) {
                    existingApproverFlows.setEffDate(approverFlows.getEffDate());
                }
                if (approverFlows.getCreatedAt() != null) {
                    existingApproverFlows.setCreatedAt(approverFlows.getCreatedAt());
                }
                if (approverFlows.getUpdatedAt() != null) {
                    existingApproverFlows.setUpdatedAt(approverFlows.getUpdatedAt());
                }
                if (approverFlows.getVersion() != null) {
                    existingApproverFlows.setVersion(approverFlows.getVersion());
                }

                return existingApproverFlows;
            })
            .map(approverFlowsRepository::save);
    }

    /**
     * Get all the approverFlows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApproverFlows> findAll(Pageable pageable) {
        log.debug("Request to get all ApproverFlows");
        return approverFlowsRepository.findAll(pageable);
    }

    /**
     * Get one approverFlows by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApproverFlows> findOne(Long id) {
        log.debug("Request to get ApproverFlows : {}", id);
        return approverFlowsRepository.findById(id);
    }

    /**
     * Delete the approverFlows by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApproverFlows : {}", id);
        approverFlowsRepository.deleteById(id);
    }
}
