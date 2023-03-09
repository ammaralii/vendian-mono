package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ClaimApprovers;
import com.venturedive.vendian_mono.repository.ClaimApproversRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClaimApprovers}.
 */
@Service
@Transactional
public class ClaimApproversService {

    private final Logger log = LoggerFactory.getLogger(ClaimApproversService.class);

    private final ClaimApproversRepository claimApproversRepository;

    public ClaimApproversService(ClaimApproversRepository claimApproversRepository) {
        this.claimApproversRepository = claimApproversRepository;
    }

    /**
     * Save a claimApprovers.
     *
     * @param claimApprovers the entity to save.
     * @return the persisted entity.
     */
    public ClaimApprovers save(ClaimApprovers claimApprovers) {
        log.debug("Request to save ClaimApprovers : {}", claimApprovers);
        return claimApproversRepository.save(claimApprovers);
    }

    /**
     * Update a claimApprovers.
     *
     * @param claimApprovers the entity to save.
     * @return the persisted entity.
     */
    public ClaimApprovers update(ClaimApprovers claimApprovers) {
        log.debug("Request to update ClaimApprovers : {}", claimApprovers);
        return claimApproversRepository.save(claimApprovers);
    }

    /**
     * Partially update a claimApprovers.
     *
     * @param claimApprovers the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClaimApprovers> partialUpdate(ClaimApprovers claimApprovers) {
        log.debug("Request to partially update ClaimApprovers : {}", claimApprovers);

        return claimApproversRepository
            .findById(claimApprovers.getId())
            .map(existingClaimApprovers -> {
                if (claimApprovers.getReferenceid() != null) {
                    existingClaimApprovers.setReferenceid(claimApprovers.getReferenceid());
                }
                if (claimApprovers.getDesignation() != null) {
                    existingClaimApprovers.setDesignation(claimApprovers.getDesignation());
                }
                if (claimApprovers.getApproveorder() != null) {
                    existingClaimApprovers.setApproveorder(claimApprovers.getApproveorder());
                }
                if (claimApprovers.getReference() != null) {
                    existingClaimApprovers.setReference(claimApprovers.getReference());
                }
                if (claimApprovers.getComments() != null) {
                    existingClaimApprovers.setComments(claimApprovers.getComments());
                }
                if (claimApprovers.getApprovedby() != null) {
                    existingClaimApprovers.setApprovedby(claimApprovers.getApprovedby());
                }
                if (claimApprovers.getCreatedat() != null) {
                    existingClaimApprovers.setCreatedat(claimApprovers.getCreatedat());
                }
                if (claimApprovers.getUpdatedat() != null) {
                    existingClaimApprovers.setUpdatedat(claimApprovers.getUpdatedat());
                }

                return existingClaimApprovers;
            })
            .map(claimApproversRepository::save);
    }

    /**
     * Get all the claimApprovers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimApprovers> findAll(Pageable pageable) {
        log.debug("Request to get all ClaimApprovers");
        return claimApproversRepository.findAll(pageable);
    }

    /**
     * Get one claimApprovers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClaimApprovers> findOne(Long id) {
        log.debug("Request to get ClaimApprovers : {}", id);
        return claimApproversRepository.findById(id);
    }

    /**
     * Delete the claimApprovers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClaimApprovers : {}", id);
        claimApproversRepository.deleteById(id);
    }
}
