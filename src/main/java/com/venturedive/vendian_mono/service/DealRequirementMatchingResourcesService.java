package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.DealRequirementMatchingResources;
import com.venturedive.vendian_mono.repository.DealRequirementMatchingResourcesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DealRequirementMatchingResources}.
 */
@Service
@Transactional
public class DealRequirementMatchingResourcesService {

    private final Logger log = LoggerFactory.getLogger(DealRequirementMatchingResourcesService.class);

    private final DealRequirementMatchingResourcesRepository dealRequirementMatchingResourcesRepository;

    public DealRequirementMatchingResourcesService(DealRequirementMatchingResourcesRepository dealRequirementMatchingResourcesRepository) {
        this.dealRequirementMatchingResourcesRepository = dealRequirementMatchingResourcesRepository;
    }

    /**
     * Save a dealRequirementMatchingResources.
     *
     * @param dealRequirementMatchingResources the entity to save.
     * @return the persisted entity.
     */
    public DealRequirementMatchingResources save(DealRequirementMatchingResources dealRequirementMatchingResources) {
        log.debug("Request to save DealRequirementMatchingResources : {}", dealRequirementMatchingResources);
        return dealRequirementMatchingResourcesRepository.save(dealRequirementMatchingResources);
    }

    /**
     * Update a dealRequirementMatchingResources.
     *
     * @param dealRequirementMatchingResources the entity to save.
     * @return the persisted entity.
     */
    public DealRequirementMatchingResources update(DealRequirementMatchingResources dealRequirementMatchingResources) {
        log.debug("Request to update DealRequirementMatchingResources : {}", dealRequirementMatchingResources);
        return dealRequirementMatchingResourcesRepository.save(dealRequirementMatchingResources);
    }

    /**
     * Partially update a dealRequirementMatchingResources.
     *
     * @param dealRequirementMatchingResources the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DealRequirementMatchingResources> partialUpdate(DealRequirementMatchingResources dealRequirementMatchingResources) {
        log.debug("Request to partially update DealRequirementMatchingResources : {}", dealRequirementMatchingResources);

        return dealRequirementMatchingResourcesRepository
            .findById(dealRequirementMatchingResources.getId())
            .map(existingDealRequirementMatchingResources -> {
                if (dealRequirementMatchingResources.getComments() != null) {
                    existingDealRequirementMatchingResources.setComments(dealRequirementMatchingResources.getComments());
                }
                if (dealRequirementMatchingResources.getCreatedat() != null) {
                    existingDealRequirementMatchingResources.setCreatedat(dealRequirementMatchingResources.getCreatedat());
                }
                if (dealRequirementMatchingResources.getUpdatedat() != null) {
                    existingDealRequirementMatchingResources.setUpdatedat(dealRequirementMatchingResources.getUpdatedat());
                }
                if (dealRequirementMatchingResources.getDeletedat() != null) {
                    existingDealRequirementMatchingResources.setDeletedat(dealRequirementMatchingResources.getDeletedat());
                }

                return existingDealRequirementMatchingResources;
            })
            .map(dealRequirementMatchingResourcesRepository::save);
    }

    /**
     * Get all the dealRequirementMatchingResources.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DealRequirementMatchingResources> findAll(Pageable pageable) {
        log.debug("Request to get all DealRequirementMatchingResources");
        return dealRequirementMatchingResourcesRepository.findAll(pageable);
    }

    /**
     * Get all the dealRequirementMatchingResources with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DealRequirementMatchingResources> findAllWithEagerRelationships(Pageable pageable) {
        return dealRequirementMatchingResourcesRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one dealRequirementMatchingResources by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DealRequirementMatchingResources> findOne(Long id) {
        log.debug("Request to get DealRequirementMatchingResources : {}", id);
        return dealRequirementMatchingResourcesRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the dealRequirementMatchingResources by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DealRequirementMatchingResources : {}", id);
        dealRequirementMatchingResourcesRepository.deleteById(id);
    }
}
