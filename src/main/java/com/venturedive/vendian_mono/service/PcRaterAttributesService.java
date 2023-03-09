package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.PcRaterAttributes;
import com.venturedive.vendian_mono.repository.PcRaterAttributesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PcRaterAttributes}.
 */
@Service
@Transactional
public class PcRaterAttributesService {

    private final Logger log = LoggerFactory.getLogger(PcRaterAttributesService.class);

    private final PcRaterAttributesRepository pcRaterAttributesRepository;

    public PcRaterAttributesService(PcRaterAttributesRepository pcRaterAttributesRepository) {
        this.pcRaterAttributesRepository = pcRaterAttributesRepository;
    }

    /**
     * Save a pcRaterAttributes.
     *
     * @param pcRaterAttributes the entity to save.
     * @return the persisted entity.
     */
    public PcRaterAttributes save(PcRaterAttributes pcRaterAttributes) {
        log.debug("Request to save PcRaterAttributes : {}", pcRaterAttributes);
        return pcRaterAttributesRepository.save(pcRaterAttributes);
    }

    /**
     * Update a pcRaterAttributes.
     *
     * @param pcRaterAttributes the entity to save.
     * @return the persisted entity.
     */
    public PcRaterAttributes update(PcRaterAttributes pcRaterAttributes) {
        log.debug("Request to update PcRaterAttributes : {}", pcRaterAttributes);
        return pcRaterAttributesRepository.save(pcRaterAttributes);
    }

    /**
     * Partially update a pcRaterAttributes.
     *
     * @param pcRaterAttributes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PcRaterAttributes> partialUpdate(PcRaterAttributes pcRaterAttributes) {
        log.debug("Request to partially update PcRaterAttributes : {}", pcRaterAttributes);

        return pcRaterAttributesRepository
            .findById(pcRaterAttributes.getId())
            .map(existingPcRaterAttributes -> {
                if (pcRaterAttributes.getPcRating() != null) {
                    existingPcRaterAttributes.setPcRating(pcRaterAttributes.getPcRating());
                }
                if (pcRaterAttributes.getPcRatingContentType() != null) {
                    existingPcRaterAttributes.setPcRatingContentType(pcRaterAttributes.getPcRatingContentType());
                }
                if (pcRaterAttributes.getComment() != null) {
                    existingPcRaterAttributes.setComment(pcRaterAttributes.getComment());
                }
                if (pcRaterAttributes.getCommentContentType() != null) {
                    existingPcRaterAttributes.setCommentContentType(pcRaterAttributes.getCommentContentType());
                }
                if (pcRaterAttributes.getCreatedAt() != null) {
                    existingPcRaterAttributes.setCreatedAt(pcRaterAttributes.getCreatedAt());
                }
                if (pcRaterAttributes.getUpdatedAt() != null) {
                    existingPcRaterAttributes.setUpdatedAt(pcRaterAttributes.getUpdatedAt());
                }
                if (pcRaterAttributes.getDeletedAt() != null) {
                    existingPcRaterAttributes.setDeletedAt(pcRaterAttributes.getDeletedAt());
                }
                if (pcRaterAttributes.getVersion() != null) {
                    existingPcRaterAttributes.setVersion(pcRaterAttributes.getVersion());
                }

                return existingPcRaterAttributes;
            })
            .map(pcRaterAttributesRepository::save);
    }

    /**
     * Get all the pcRaterAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PcRaterAttributes> findAll(Pageable pageable) {
        log.debug("Request to get all PcRaterAttributes");
        return pcRaterAttributesRepository.findAll(pageable);
    }

    /**
     * Get one pcRaterAttributes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PcRaterAttributes> findOne(Long id) {
        log.debug("Request to get PcRaterAttributes : {}", id);
        return pcRaterAttributesRepository.findById(id);
    }

    /**
     * Delete the pcRaterAttributes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PcRaterAttributes : {}", id);
        pcRaterAttributesRepository.deleteById(id);
    }
}
