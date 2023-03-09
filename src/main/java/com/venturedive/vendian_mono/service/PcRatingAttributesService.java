package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.PcRatingAttributes;
import com.venturedive.vendian_mono.repository.PcRatingAttributesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PcRatingAttributes}.
 */
@Service
@Transactional
public class PcRatingAttributesService {

    private final Logger log = LoggerFactory.getLogger(PcRatingAttributesService.class);

    private final PcRatingAttributesRepository pcRatingAttributesRepository;

    public PcRatingAttributesService(PcRatingAttributesRepository pcRatingAttributesRepository) {
        this.pcRatingAttributesRepository = pcRatingAttributesRepository;
    }

    /**
     * Save a pcRatingAttributes.
     *
     * @param pcRatingAttributes the entity to save.
     * @return the persisted entity.
     */
    public PcRatingAttributes save(PcRatingAttributes pcRatingAttributes) {
        log.debug("Request to save PcRatingAttributes : {}", pcRatingAttributes);
        return pcRatingAttributesRepository.save(pcRatingAttributes);
    }

    /**
     * Update a pcRatingAttributes.
     *
     * @param pcRatingAttributes the entity to save.
     * @return the persisted entity.
     */
    public PcRatingAttributes update(PcRatingAttributes pcRatingAttributes) {
        log.debug("Request to update PcRatingAttributes : {}", pcRatingAttributes);
        return pcRatingAttributesRepository.save(pcRatingAttributes);
    }

    /**
     * Partially update a pcRatingAttributes.
     *
     * @param pcRatingAttributes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PcRatingAttributes> partialUpdate(PcRatingAttributes pcRatingAttributes) {
        log.debug("Request to partially update PcRatingAttributes : {}", pcRatingAttributes);

        return pcRatingAttributesRepository
            .findById(pcRatingAttributes.getId())
            .map(existingPcRatingAttributes -> {
                if (pcRatingAttributes.getName() != null) {
                    existingPcRatingAttributes.setName(pcRatingAttributes.getName());
                }
                if (pcRatingAttributes.getEffDate() != null) {
                    existingPcRatingAttributes.setEffDate(pcRatingAttributes.getEffDate());
                }
                if (pcRatingAttributes.getCreatedAt() != null) {
                    existingPcRatingAttributes.setCreatedAt(pcRatingAttributes.getCreatedAt());
                }
                if (pcRatingAttributes.getUpdatedAt() != null) {
                    existingPcRatingAttributes.setUpdatedAt(pcRatingAttributes.getUpdatedAt());
                }
                if (pcRatingAttributes.getEndDate() != null) {
                    existingPcRatingAttributes.setEndDate(pcRatingAttributes.getEndDate());
                }
                if (pcRatingAttributes.getVersion() != null) {
                    existingPcRatingAttributes.setVersion(pcRatingAttributes.getVersion());
                }
                if (pcRatingAttributes.getSubCategory() != null) {
                    existingPcRatingAttributes.setSubCategory(pcRatingAttributes.getSubCategory());
                }
                if (pcRatingAttributes.getDescription() != null) {
                    existingPcRatingAttributes.setDescription(pcRatingAttributes.getDescription());
                }

                return existingPcRatingAttributes;
            })
            .map(pcRatingAttributesRepository::save);
    }

    /**
     * Get all the pcRatingAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PcRatingAttributes> findAll(Pageable pageable) {
        log.debug("Request to get all PcRatingAttributes");
        return pcRatingAttributesRepository.findAll(pageable);
    }

    /**
     * Get one pcRatingAttributes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PcRatingAttributes> findOne(Long id) {
        log.debug("Request to get PcRatingAttributes : {}", id);
        return pcRatingAttributesRepository.findById(id);
    }

    /**
     * Delete the pcRatingAttributes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PcRatingAttributes : {}", id);
        pcRatingAttributesRepository.deleteById(id);
    }
}
