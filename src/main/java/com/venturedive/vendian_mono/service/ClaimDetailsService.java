package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ClaimDetails;
import com.venturedive.vendian_mono.repository.ClaimDetailsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClaimDetails}.
 */
@Service
@Transactional
public class ClaimDetailsService {

    private final Logger log = LoggerFactory.getLogger(ClaimDetailsService.class);

    private final ClaimDetailsRepository claimDetailsRepository;

    public ClaimDetailsService(ClaimDetailsRepository claimDetailsRepository) {
        this.claimDetailsRepository = claimDetailsRepository;
    }

    /**
     * Save a claimDetails.
     *
     * @param claimDetails the entity to save.
     * @return the persisted entity.
     */
    public ClaimDetails save(ClaimDetails claimDetails) {
        log.debug("Request to save ClaimDetails : {}", claimDetails);
        return claimDetailsRepository.save(claimDetails);
    }

    /**
     * Update a claimDetails.
     *
     * @param claimDetails the entity to save.
     * @return the persisted entity.
     */
    public ClaimDetails update(ClaimDetails claimDetails) {
        log.debug("Request to update ClaimDetails : {}", claimDetails);
        return claimDetailsRepository.save(claimDetails);
    }

    /**
     * Partially update a claimDetails.
     *
     * @param claimDetails the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClaimDetails> partialUpdate(ClaimDetails claimDetails) {
        log.debug("Request to partially update ClaimDetails : {}", claimDetails);

        return claimDetailsRepository
            .findById(claimDetails.getId())
            .map(existingClaimDetails -> {
                if (claimDetails.getAmount() != null) {
                    existingClaimDetails.setAmount(claimDetails.getAmount());
                }
                if (claimDetails.getStartdate() != null) {
                    existingClaimDetails.setStartdate(claimDetails.getStartdate());
                }
                if (claimDetails.getEnddate() != null) {
                    existingClaimDetails.setEnddate(claimDetails.getEnddate());
                }
                if (claimDetails.getDescription() != null) {
                    existingClaimDetails.setDescription(claimDetails.getDescription());
                }
                if (claimDetails.getCreatedat() != null) {
                    existingClaimDetails.setCreatedat(claimDetails.getCreatedat());
                }
                if (claimDetails.getUpdatedat() != null) {
                    existingClaimDetails.setUpdatedat(claimDetails.getUpdatedat());
                }

                return existingClaimDetails;
            })
            .map(claimDetailsRepository::save);
    }

    /**
     * Get all the claimDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimDetails> findAll(Pageable pageable) {
        log.debug("Request to get all ClaimDetails");
        return claimDetailsRepository.findAll(pageable);
    }

    /**
     * Get one claimDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClaimDetails> findOne(Long id) {
        log.debug("Request to get ClaimDetails : {}", id);
        return claimDetailsRepository.findById(id);
    }

    /**
     * Delete the claimDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClaimDetails : {}", id);
        claimDetailsRepository.deleteById(id);
    }
}
