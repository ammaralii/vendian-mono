package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ClaimRequests;
import com.venturedive.vendian_mono.repository.ClaimRequestsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClaimRequests}.
 */
@Service
@Transactional
public class ClaimRequestsService {

    private final Logger log = LoggerFactory.getLogger(ClaimRequestsService.class);

    private final ClaimRequestsRepository claimRequestsRepository;

    public ClaimRequestsService(ClaimRequestsRepository claimRequestsRepository) {
        this.claimRequestsRepository = claimRequestsRepository;
    }

    /**
     * Save a claimRequests.
     *
     * @param claimRequests the entity to save.
     * @return the persisted entity.
     */
    public ClaimRequests save(ClaimRequests claimRequests) {
        log.debug("Request to save ClaimRequests : {}", claimRequests);
        return claimRequestsRepository.save(claimRequests);
    }

    /**
     * Update a claimRequests.
     *
     * @param claimRequests the entity to save.
     * @return the persisted entity.
     */
    public ClaimRequests update(ClaimRequests claimRequests) {
        log.debug("Request to update ClaimRequests : {}", claimRequests);
        return claimRequestsRepository.save(claimRequests);
    }

    /**
     * Partially update a claimRequests.
     *
     * @param claimRequests the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClaimRequests> partialUpdate(ClaimRequests claimRequests) {
        log.debug("Request to partially update ClaimRequests : {}", claimRequests);

        return claimRequestsRepository
            .findById(claimRequests.getId())
            .map(existingClaimRequests -> {
                if (claimRequests.getProjectid() != null) {
                    existingClaimRequests.setProjectid(claimRequests.getProjectid());
                }
                if (claimRequests.getComments() != null) {
                    existingClaimRequests.setComments(claimRequests.getComments());
                }
                if (claimRequests.getAmountreleased() != null) {
                    existingClaimRequests.setAmountreleased(claimRequests.getAmountreleased());
                }
                if (claimRequests.getDesignation() != null) {
                    existingClaimRequests.setDesignation(claimRequests.getDesignation());
                }
                if (claimRequests.getDepartment() != null) {
                    existingClaimRequests.setDepartment(claimRequests.getDepartment());
                }
                if (claimRequests.getLocation() != null) {
                    existingClaimRequests.setLocation(claimRequests.getLocation());
                }
                if (claimRequests.getManager() != null) {
                    existingClaimRequests.setManager(claimRequests.getManager());
                }
                if (claimRequests.getCreatedat() != null) {
                    existingClaimRequests.setCreatedat(claimRequests.getCreatedat());
                }
                if (claimRequests.getUpdatedat() != null) {
                    existingClaimRequests.setUpdatedat(claimRequests.getUpdatedat());
                }

                return existingClaimRequests;
            })
            .map(claimRequestsRepository::save);
    }

    /**
     * Get all the claimRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimRequests> findAll(Pageable pageable) {
        log.debug("Request to get all ClaimRequests");
        return claimRequestsRepository.findAll(pageable);
    }

    /**
     * Get one claimRequests by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClaimRequests> findOne(Long id) {
        log.debug("Request to get ClaimRequests : {}", id);
        return claimRequestsRepository.findById(id);
    }

    /**
     * Delete the claimRequests by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClaimRequests : {}", id);
        claimRequestsRepository.deleteById(id);
    }
}
