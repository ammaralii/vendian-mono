package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Benefits;
import com.venturedive.vendian_mono.repository.BenefitsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Benefits}.
 */
@Service
@Transactional
public class BenefitsService {

    private final Logger log = LoggerFactory.getLogger(BenefitsService.class);

    private final BenefitsRepository benefitsRepository;

    public BenefitsService(BenefitsRepository benefitsRepository) {
        this.benefitsRepository = benefitsRepository;
    }

    /**
     * Save a benefits.
     *
     * @param benefits the entity to save.
     * @return the persisted entity.
     */
    public Benefits save(Benefits benefits) {
        log.debug("Request to save Benefits : {}", benefits);
        return benefitsRepository.save(benefits);
    }

    /**
     * Update a benefits.
     *
     * @param benefits the entity to save.
     * @return the persisted entity.
     */
    public Benefits update(Benefits benefits) {
        log.debug("Request to update Benefits : {}", benefits);
        return benefitsRepository.save(benefits);
    }

    /**
     * Partially update a benefits.
     *
     * @param benefits the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Benefits> partialUpdate(Benefits benefits) {
        log.debug("Request to partially update Benefits : {}", benefits);

        return benefitsRepository
            .findById(benefits.getId())
            .map(existingBenefits -> {
                if (benefits.getName() != null) {
                    existingBenefits.setName(benefits.getName());
                }
                if (benefits.getIsdeleted() != null) {
                    existingBenefits.setIsdeleted(benefits.getIsdeleted());
                }
                if (benefits.getCreatedat() != null) {
                    existingBenefits.setCreatedat(benefits.getCreatedat());
                }
                if (benefits.getUpdatedat() != null) {
                    existingBenefits.setUpdatedat(benefits.getUpdatedat());
                }

                return existingBenefits;
            })
            .map(benefitsRepository::save);
    }

    /**
     * Get all the benefits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Benefits> findAll(Pageable pageable) {
        log.debug("Request to get all Benefits");
        return benefitsRepository.findAll(pageable);
    }

    /**
     * Get one benefits by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Benefits> findOne(Long id) {
        log.debug("Request to get Benefits : {}", id);
        return benefitsRepository.findById(id);
    }

    /**
     * Delete the benefits by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Benefits : {}", id);
        benefitsRepository.deleteById(id);
    }
}
