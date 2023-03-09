package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.DealRequirements;
import com.venturedive.vendian_mono.repository.DealRequirementsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DealRequirements}.
 */
@Service
@Transactional
public class DealRequirementsService {

    private final Logger log = LoggerFactory.getLogger(DealRequirementsService.class);

    private final DealRequirementsRepository dealRequirementsRepository;

    public DealRequirementsService(DealRequirementsRepository dealRequirementsRepository) {
        this.dealRequirementsRepository = dealRequirementsRepository;
    }

    /**
     * Save a dealRequirements.
     *
     * @param dealRequirements the entity to save.
     * @return the persisted entity.
     */
    public DealRequirements save(DealRequirements dealRequirements) {
        log.debug("Request to save DealRequirements : {}", dealRequirements);
        return dealRequirementsRepository.save(dealRequirements);
    }

    /**
     * Update a dealRequirements.
     *
     * @param dealRequirements the entity to save.
     * @return the persisted entity.
     */
    public DealRequirements update(DealRequirements dealRequirements) {
        log.debug("Request to update DealRequirements : {}", dealRequirements);
        return dealRequirementsRepository.save(dealRequirements);
    }

    /**
     * Partially update a dealRequirements.
     *
     * @param dealRequirements the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DealRequirements> partialUpdate(DealRequirements dealRequirements) {
        log.debug("Request to partially update DealRequirements : {}", dealRequirements);

        return dealRequirementsRepository
            .findById(dealRequirements.getId())
            .map(existingDealRequirements -> {
                if (dealRequirements.getDealreqidentifier() != null) {
                    existingDealRequirements.setDealreqidentifier(dealRequirements.getDealreqidentifier());
                }
                if (dealRequirements.getCompetencyname() != null) {
                    existingDealRequirements.setCompetencyname(dealRequirements.getCompetencyname());
                }
                if (dealRequirements.getSkills() != null) {
                    existingDealRequirements.setSkills(dealRequirements.getSkills());
                }
                if (dealRequirements.getResourcerequired() != null) {
                    existingDealRequirements.setResourcerequired(dealRequirements.getResourcerequired());
                }
                if (dealRequirements.getMinexperiencelevel() != null) {
                    existingDealRequirements.setMinexperiencelevel(dealRequirements.getMinexperiencelevel());
                }
                if (dealRequirements.getMaxexperiencelevel() != null) {
                    existingDealRequirements.setMaxexperiencelevel(dealRequirements.getMaxexperiencelevel());
                }
                if (dealRequirements.getCreatedat() != null) {
                    existingDealRequirements.setCreatedat(dealRequirements.getCreatedat());
                }
                if (dealRequirements.getUpdatedat() != null) {
                    existingDealRequirements.setUpdatedat(dealRequirements.getUpdatedat());
                }
                if (dealRequirements.getDeletedat() != null) {
                    existingDealRequirements.setDeletedat(dealRequirements.getDeletedat());
                }

                return existingDealRequirements;
            })
            .map(dealRequirementsRepository::save);
    }

    /**
     * Get all the dealRequirements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DealRequirements> findAll(Pageable pageable) {
        log.debug("Request to get all DealRequirements");
        return dealRequirementsRepository.findAll(pageable);
    }

    /**
     * Get one dealRequirements by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DealRequirements> findOne(Long id) {
        log.debug("Request to get DealRequirements : {}", id);
        return dealRequirementsRepository.findById(id);
    }

    /**
     * Delete the dealRequirements by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DealRequirements : {}", id);
        dealRequirementsRepository.deleteById(id);
    }
}
