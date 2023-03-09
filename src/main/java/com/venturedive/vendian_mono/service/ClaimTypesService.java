package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ClaimTypes;
import com.venturedive.vendian_mono.repository.ClaimTypesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClaimTypes}.
 */
@Service
@Transactional
public class ClaimTypesService {

    private final Logger log = LoggerFactory.getLogger(ClaimTypesService.class);

    private final ClaimTypesRepository claimTypesRepository;

    public ClaimTypesService(ClaimTypesRepository claimTypesRepository) {
        this.claimTypesRepository = claimTypesRepository;
    }

    /**
     * Save a claimTypes.
     *
     * @param claimTypes the entity to save.
     * @return the persisted entity.
     */
    public ClaimTypes save(ClaimTypes claimTypes) {
        log.debug("Request to save ClaimTypes : {}", claimTypes);
        return claimTypesRepository.save(claimTypes);
    }

    /**
     * Update a claimTypes.
     *
     * @param claimTypes the entity to save.
     * @return the persisted entity.
     */
    public ClaimTypes update(ClaimTypes claimTypes) {
        log.debug("Request to update ClaimTypes : {}", claimTypes);
        return claimTypesRepository.save(claimTypes);
    }

    /**
     * Partially update a claimTypes.
     *
     * @param claimTypes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClaimTypes> partialUpdate(ClaimTypes claimTypes) {
        log.debug("Request to partially update ClaimTypes : {}", claimTypes);

        return claimTypesRepository
            .findById(claimTypes.getId())
            .map(existingClaimTypes -> {
                if (claimTypes.getClaimtype() != null) {
                    existingClaimTypes.setClaimtype(claimTypes.getClaimtype());
                }
                if (claimTypes.getDaterangerequired() != null) {
                    existingClaimTypes.setDaterangerequired(claimTypes.getDaterangerequired());
                }
                if (claimTypes.getDescriptionrequired() != null) {
                    existingClaimTypes.setDescriptionrequired(claimTypes.getDescriptionrequired());
                }
                if (claimTypes.getParentid() != null) {
                    existingClaimTypes.setParentid(claimTypes.getParentid());
                }
                if (claimTypes.getCreatedat() != null) {
                    existingClaimTypes.setCreatedat(claimTypes.getCreatedat());
                }
                if (claimTypes.getUpdatedat() != null) {
                    existingClaimTypes.setUpdatedat(claimTypes.getUpdatedat());
                }

                return existingClaimTypes;
            })
            .map(claimTypesRepository::save);
    }

    /**
     * Get all the claimTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimTypes> findAll(Pageable pageable) {
        log.debug("Request to get all ClaimTypes");
        return claimTypesRepository.findAll(pageable);
    }

    /**
     * Get one claimTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClaimTypes> findOne(Long id) {
        log.debug("Request to get ClaimTypes : {}", id);
        return claimTypesRepository.findById(id);
    }

    /**
     * Delete the claimTypes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClaimTypes : {}", id);
        claimTypesRepository.deleteById(id);
    }
}
