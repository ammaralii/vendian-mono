package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.BusinessUnits;
import com.venturedive.vendian_mono.repository.BusinessUnitsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BusinessUnits}.
 */
@Service
@Transactional
public class BusinessUnitsService {

    private final Logger log = LoggerFactory.getLogger(BusinessUnitsService.class);

    private final BusinessUnitsRepository businessUnitsRepository;

    public BusinessUnitsService(BusinessUnitsRepository businessUnitsRepository) {
        this.businessUnitsRepository = businessUnitsRepository;
    }

    /**
     * Save a businessUnits.
     *
     * @param businessUnits the entity to save.
     * @return the persisted entity.
     */
    public BusinessUnits save(BusinessUnits businessUnits) {
        log.debug("Request to save BusinessUnits : {}", businessUnits);
        return businessUnitsRepository.save(businessUnits);
    }

    /**
     * Update a businessUnits.
     *
     * @param businessUnits the entity to save.
     * @return the persisted entity.
     */
    public BusinessUnits update(BusinessUnits businessUnits) {
        log.debug("Request to update BusinessUnits : {}", businessUnits);
        return businessUnitsRepository.save(businessUnits);
    }

    /**
     * Partially update a businessUnits.
     *
     * @param businessUnits the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BusinessUnits> partialUpdate(BusinessUnits businessUnits) {
        log.debug("Request to partially update BusinessUnits : {}", businessUnits);

        return businessUnitsRepository
            .findById(businessUnits.getId())
            .map(existingBusinessUnits -> {
                if (businessUnits.getName() != null) {
                    existingBusinessUnits.setName(businessUnits.getName());
                }
                if (businessUnits.getCreatedat() != null) {
                    existingBusinessUnits.setCreatedat(businessUnits.getCreatedat());
                }
                if (businessUnits.getUpdatedat() != null) {
                    existingBusinessUnits.setUpdatedat(businessUnits.getUpdatedat());
                }

                return existingBusinessUnits;
            })
            .map(businessUnitsRepository::save);
    }

    /**
     * Get all the businessUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessUnits> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessUnits");
        return businessUnitsRepository.findAll(pageable);
    }

    /**
     * Get one businessUnits by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BusinessUnits> findOne(Long id) {
        log.debug("Request to get BusinessUnits : {}", id);
        return businessUnitsRepository.findById(id);
    }

    /**
     * Delete the businessUnits by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BusinessUnits : {}", id);
        businessUnitsRepository.deleteById(id);
    }
}
