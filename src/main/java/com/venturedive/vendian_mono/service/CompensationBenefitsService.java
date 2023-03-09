package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.CompensationBenefits;
import com.venturedive.vendian_mono.repository.CompensationBenefitsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompensationBenefits}.
 */
@Service
@Transactional
public class CompensationBenefitsService {

    private final Logger log = LoggerFactory.getLogger(CompensationBenefitsService.class);

    private final CompensationBenefitsRepository compensationBenefitsRepository;

    public CompensationBenefitsService(CompensationBenefitsRepository compensationBenefitsRepository) {
        this.compensationBenefitsRepository = compensationBenefitsRepository;
    }

    /**
     * Save a compensationBenefits.
     *
     * @param compensationBenefits the entity to save.
     * @return the persisted entity.
     */
    public CompensationBenefits save(CompensationBenefits compensationBenefits) {
        log.debug("Request to save CompensationBenefits : {}", compensationBenefits);
        return compensationBenefitsRepository.save(compensationBenefits);
    }

    /**
     * Update a compensationBenefits.
     *
     * @param compensationBenefits the entity to save.
     * @return the persisted entity.
     */
    public CompensationBenefits update(CompensationBenefits compensationBenefits) {
        log.debug("Request to update CompensationBenefits : {}", compensationBenefits);
        return compensationBenefitsRepository.save(compensationBenefits);
    }

    /**
     * Partially update a compensationBenefits.
     *
     * @param compensationBenefits the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CompensationBenefits> partialUpdate(CompensationBenefits compensationBenefits) {
        log.debug("Request to partially update CompensationBenefits : {}", compensationBenefits);

        return compensationBenefitsRepository
            .findById(compensationBenefits.getId())
            .map(existingCompensationBenefits -> {
                if (compensationBenefits.getAmount() != null) {
                    existingCompensationBenefits.setAmount(compensationBenefits.getAmount());
                }
                if (compensationBenefits.getIsdeleted() != null) {
                    existingCompensationBenefits.setIsdeleted(compensationBenefits.getIsdeleted());
                }
                if (compensationBenefits.getCreatedat() != null) {
                    existingCompensationBenefits.setCreatedat(compensationBenefits.getCreatedat());
                }
                if (compensationBenefits.getUpdatedat() != null) {
                    existingCompensationBenefits.setUpdatedat(compensationBenefits.getUpdatedat());
                }

                return existingCompensationBenefits;
            })
            .map(compensationBenefitsRepository::save);
    }

    /**
     * Get all the compensationBenefits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompensationBenefits> findAll(Pageable pageable) {
        log.debug("Request to get all CompensationBenefits");
        return compensationBenefitsRepository.findAll(pageable);
    }

    /**
     * Get all the compensationBenefits with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CompensationBenefits> findAllWithEagerRelationships(Pageable pageable) {
        return compensationBenefitsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one compensationBenefits by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompensationBenefits> findOne(Long id) {
        log.debug("Request to get CompensationBenefits : {}", id);
        return compensationBenefitsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the compensationBenefits by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompensationBenefits : {}", id);
        compensationBenefitsRepository.deleteById(id);
    }
}
