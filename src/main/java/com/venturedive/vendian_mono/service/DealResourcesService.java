package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.DealResources;
import com.venturedive.vendian_mono.repository.DealResourcesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DealResources}.
 */
@Service
@Transactional
public class DealResourcesService {

    private final Logger log = LoggerFactory.getLogger(DealResourcesService.class);

    private final DealResourcesRepository dealResourcesRepository;

    public DealResourcesService(DealResourcesRepository dealResourcesRepository) {
        this.dealResourcesRepository = dealResourcesRepository;
    }

    /**
     * Save a dealResources.
     *
     * @param dealResources the entity to save.
     * @return the persisted entity.
     */
    public DealResources save(DealResources dealResources) {
        log.debug("Request to save DealResources : {}", dealResources);
        return dealResourcesRepository.save(dealResources);
    }

    /**
     * Update a dealResources.
     *
     * @param dealResources the entity to save.
     * @return the persisted entity.
     */
    public DealResources update(DealResources dealResources) {
        log.debug("Request to update DealResources : {}", dealResources);
        return dealResourcesRepository.save(dealResources);
    }

    /**
     * Partially update a dealResources.
     *
     * @param dealResources the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DealResources> partialUpdate(DealResources dealResources) {
        log.debug("Request to partially update DealResources : {}", dealResources);

        return dealResourcesRepository
            .findById(dealResources.getId())
            .map(existingDealResources -> {
                if (dealResources.getFirstname() != null) {
                    existingDealResources.setFirstname(dealResources.getFirstname());
                }
                if (dealResources.getLastname() != null) {
                    existingDealResources.setLastname(dealResources.getLastname());
                }
                if (dealResources.getJoiningdate() != null) {
                    existingDealResources.setJoiningdate(dealResources.getJoiningdate());
                }
                if (dealResources.getExternalexpyears() != null) {
                    existingDealResources.setExternalexpyears(dealResources.getExternalexpyears());
                }
                if (dealResources.getExternalexpmonths() != null) {
                    existingDealResources.setExternalexpmonths(dealResources.getExternalexpmonths());
                }
                if (dealResources.getCreatedat() != null) {
                    existingDealResources.setCreatedat(dealResources.getCreatedat());
                }
                if (dealResources.getUpdatedat() != null) {
                    existingDealResources.setUpdatedat(dealResources.getUpdatedat());
                }
                if (dealResources.getType() != null) {
                    existingDealResources.setType(dealResources.getType());
                }
                if (dealResources.getIsactive() != null) {
                    existingDealResources.setIsactive(dealResources.getIsactive());
                }

                return existingDealResources;
            })
            .map(dealResourcesRepository::save);
    }

    /**
     * Get all the dealResources.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DealResources> findAll(Pageable pageable) {
        log.debug("Request to get all DealResources");
        return dealResourcesRepository.findAll(pageable);
    }

    /**
     * Get one dealResources by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DealResources> findOne(Long id) {
        log.debug("Request to get DealResources : {}", id);
        return dealResourcesRepository.findById(id);
    }

    /**
     * Delete the dealResources by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DealResources : {}", id);
        dealResourcesRepository.deleteById(id);
    }
}
