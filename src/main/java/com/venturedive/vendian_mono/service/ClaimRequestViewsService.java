package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ClaimRequestViews;
import com.venturedive.vendian_mono.repository.ClaimRequestViewsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClaimRequestViews}.
 */
@Service
@Transactional
public class ClaimRequestViewsService {

    private final Logger log = LoggerFactory.getLogger(ClaimRequestViewsService.class);

    private final ClaimRequestViewsRepository claimRequestViewsRepository;

    public ClaimRequestViewsService(ClaimRequestViewsRepository claimRequestViewsRepository) {
        this.claimRequestViewsRepository = claimRequestViewsRepository;
    }

    /**
     * Save a claimRequestViews.
     *
     * @param claimRequestViews the entity to save.
     * @return the persisted entity.
     */
    public ClaimRequestViews save(ClaimRequestViews claimRequestViews) {
        log.debug("Request to save ClaimRequestViews : {}", claimRequestViews);
        return claimRequestViewsRepository.save(claimRequestViews);
    }

    /**
     * Update a claimRequestViews.
     *
     * @param claimRequestViews the entity to save.
     * @return the persisted entity.
     */
    public ClaimRequestViews update(ClaimRequestViews claimRequestViews) {
        log.debug("Request to update ClaimRequestViews : {}", claimRequestViews);
        return claimRequestViewsRepository.save(claimRequestViews);
    }

    /**
     * Partially update a claimRequestViews.
     *
     * @param claimRequestViews the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClaimRequestViews> partialUpdate(ClaimRequestViews claimRequestViews) {
        log.debug("Request to partially update ClaimRequestViews : {}", claimRequestViews);

        return claimRequestViewsRepository
            .findById(claimRequestViews.getId())
            .map(existingClaimRequestViews -> {
                if (claimRequestViews.getCostcenter() != null) {
                    existingClaimRequestViews.setCostcenter(claimRequestViews.getCostcenter());
                }
                if (claimRequestViews.getComments() != null) {
                    existingClaimRequestViews.setComments(claimRequestViews.getComments());
                }
                if (claimRequestViews.getAmountreleased() != null) {
                    existingClaimRequestViews.setAmountreleased(claimRequestViews.getAmountreleased());
                }
                if (claimRequestViews.getDesignation() != null) {
                    existingClaimRequestViews.setDesignation(claimRequestViews.getDesignation());
                }
                if (claimRequestViews.getDepartment() != null) {
                    existingClaimRequestViews.setDepartment(claimRequestViews.getDepartment());
                }
                if (claimRequestViews.getLocation() != null) {
                    existingClaimRequestViews.setLocation(claimRequestViews.getLocation());
                }
                if (claimRequestViews.getManager() != null) {
                    existingClaimRequestViews.setManager(claimRequestViews.getManager());
                }
                if (claimRequestViews.getCreatedat() != null) {
                    existingClaimRequestViews.setCreatedat(claimRequestViews.getCreatedat());
                }
                if (claimRequestViews.getUpdatedat() != null) {
                    existingClaimRequestViews.setUpdatedat(claimRequestViews.getUpdatedat());
                }

                return existingClaimRequestViews;
            })
            .map(claimRequestViewsRepository::save);
    }

    /**
     * Get all the claimRequestViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimRequestViews> findAll(Pageable pageable) {
        log.debug("Request to get all ClaimRequestViews");
        return claimRequestViewsRepository.findAll(pageable);
    }

    /**
     * Get one claimRequestViews by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClaimRequestViews> findOne(Long id) {
        log.debug("Request to get ClaimRequestViews : {}", id);
        return claimRequestViewsRepository.findById(id);
    }

    /**
     * Delete the claimRequestViews by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClaimRequestViews : {}", id);
        claimRequestViewsRepository.deleteById(id);
    }
}
