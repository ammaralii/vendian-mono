package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.PcRatingAttributesCategories;
import com.venturedive.vendian_mono.repository.PcRatingAttributesCategoriesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PcRatingAttributesCategories}.
 */
@Service
@Transactional
public class PcRatingAttributesCategoriesService {

    private final Logger log = LoggerFactory.getLogger(PcRatingAttributesCategoriesService.class);

    private final PcRatingAttributesCategoriesRepository pcRatingAttributesCategoriesRepository;

    public PcRatingAttributesCategoriesService(PcRatingAttributesCategoriesRepository pcRatingAttributesCategoriesRepository) {
        this.pcRatingAttributesCategoriesRepository = pcRatingAttributesCategoriesRepository;
    }

    /**
     * Save a pcRatingAttributesCategories.
     *
     * @param pcRatingAttributesCategories the entity to save.
     * @return the persisted entity.
     */
    public PcRatingAttributesCategories save(PcRatingAttributesCategories pcRatingAttributesCategories) {
        log.debug("Request to save PcRatingAttributesCategories : {}", pcRatingAttributesCategories);
        return pcRatingAttributesCategoriesRepository.save(pcRatingAttributesCategories);
    }

    /**
     * Update a pcRatingAttributesCategories.
     *
     * @param pcRatingAttributesCategories the entity to save.
     * @return the persisted entity.
     */
    public PcRatingAttributesCategories update(PcRatingAttributesCategories pcRatingAttributesCategories) {
        log.debug("Request to update PcRatingAttributesCategories : {}", pcRatingAttributesCategories);
        return pcRatingAttributesCategoriesRepository.save(pcRatingAttributesCategories);
    }

    /**
     * Partially update a pcRatingAttributesCategories.
     *
     * @param pcRatingAttributesCategories the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PcRatingAttributesCategories> partialUpdate(PcRatingAttributesCategories pcRatingAttributesCategories) {
        log.debug("Request to partially update PcRatingAttributesCategories : {}", pcRatingAttributesCategories);

        return pcRatingAttributesCategoriesRepository
            .findById(pcRatingAttributesCategories.getId())
            .map(existingPcRatingAttributesCategories -> {
                if (pcRatingAttributesCategories.getEffDate() != null) {
                    existingPcRatingAttributesCategories.setEffDate(pcRatingAttributesCategories.getEffDate());
                }
                if (pcRatingAttributesCategories.getCreatedAt() != null) {
                    existingPcRatingAttributesCategories.setCreatedAt(pcRatingAttributesCategories.getCreatedAt());
                }
                if (pcRatingAttributesCategories.getUpdatedAt() != null) {
                    existingPcRatingAttributesCategories.setUpdatedAt(pcRatingAttributesCategories.getUpdatedAt());
                }
                if (pcRatingAttributesCategories.getEndDate() != null) {
                    existingPcRatingAttributesCategories.setEndDate(pcRatingAttributesCategories.getEndDate());
                }
                if (pcRatingAttributesCategories.getVersion() != null) {
                    existingPcRatingAttributesCategories.setVersion(pcRatingAttributesCategories.getVersion());
                }

                return existingPcRatingAttributesCategories;
            })
            .map(pcRatingAttributesCategoriesRepository::save);
    }

    /**
     * Get all the pcRatingAttributesCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PcRatingAttributesCategories> findAll(Pageable pageable) {
        log.debug("Request to get all PcRatingAttributesCategories");
        return pcRatingAttributesCategoriesRepository.findAll(pageable);
    }

    /**
     * Get one pcRatingAttributesCategories by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PcRatingAttributesCategories> findOne(Long id) {
        log.debug("Request to get PcRatingAttributesCategories : {}", id);
        return pcRatingAttributesCategoriesRepository.findById(id);
    }

    /**
     * Delete the pcRatingAttributesCategories by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PcRatingAttributesCategories : {}", id);
        pcRatingAttributesCategoriesRepository.deleteById(id);
    }
}
