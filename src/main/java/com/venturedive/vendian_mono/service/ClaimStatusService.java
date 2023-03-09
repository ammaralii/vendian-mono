package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ClaimStatus;
import com.venturedive.vendian_mono.repository.ClaimStatusRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClaimStatus}.
 */
@Service
@Transactional
public class ClaimStatusService {

    private final Logger log = LoggerFactory.getLogger(ClaimStatusService.class);

    private final ClaimStatusRepository claimStatusRepository;

    public ClaimStatusService(ClaimStatusRepository claimStatusRepository) {
        this.claimStatusRepository = claimStatusRepository;
    }

    /**
     * Save a claimStatus.
     *
     * @param claimStatus the entity to save.
     * @return the persisted entity.
     */
    public ClaimStatus save(ClaimStatus claimStatus) {
        log.debug("Request to save ClaimStatus : {}", claimStatus);
        return claimStatusRepository.save(claimStatus);
    }

    /**
     * Update a claimStatus.
     *
     * @param claimStatus the entity to save.
     * @return the persisted entity.
     */
    public ClaimStatus update(ClaimStatus claimStatus) {
        log.debug("Request to update ClaimStatus : {}", claimStatus);
        return claimStatusRepository.save(claimStatus);
    }

    /**
     * Partially update a claimStatus.
     *
     * @param claimStatus the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClaimStatus> partialUpdate(ClaimStatus claimStatus) {
        log.debug("Request to partially update ClaimStatus : {}", claimStatus);

        return claimStatusRepository
            .findById(claimStatus.getId())
            .map(existingClaimStatus -> {
                if (claimStatus.getStatus() != null) {
                    existingClaimStatus.setStatus(claimStatus.getStatus());
                }
                if (claimStatus.getCreatedat() != null) {
                    existingClaimStatus.setCreatedat(claimStatus.getCreatedat());
                }
                if (claimStatus.getUpdatedat() != null) {
                    existingClaimStatus.setUpdatedat(claimStatus.getUpdatedat());
                }

                return existingClaimStatus;
            })
            .map(claimStatusRepository::save);
    }

    /**
     * Get all the claimStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimStatus> findAll(Pageable pageable) {
        log.debug("Request to get all ClaimStatuses");
        return claimStatusRepository.findAll(pageable);
    }

    /**
     * Get one claimStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClaimStatus> findOne(Long id) {
        log.debug("Request to get ClaimStatus : {}", id);
        return claimStatusRepository.findById(id);
    }

    /**
     * Delete the claimStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClaimStatus : {}", id);
        claimStatusRepository.deleteById(id);
    }
}
