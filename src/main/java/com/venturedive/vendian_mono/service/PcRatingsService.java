package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.PcRatings;
import com.venturedive.vendian_mono.repository.PcRatingsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PcRatings}.
 */
@Service
@Transactional
public class PcRatingsService {

    private final Logger log = LoggerFactory.getLogger(PcRatingsService.class);

    private final PcRatingsRepository pcRatingsRepository;

    public PcRatingsService(PcRatingsRepository pcRatingsRepository) {
        this.pcRatingsRepository = pcRatingsRepository;
    }

    /**
     * Save a pcRatings.
     *
     * @param pcRatings the entity to save.
     * @return the persisted entity.
     */
    public PcRatings save(PcRatings pcRatings) {
        log.debug("Request to save PcRatings : {}", pcRatings);
        return pcRatingsRepository.save(pcRatings);
    }

    /**
     * Update a pcRatings.
     *
     * @param pcRatings the entity to save.
     * @return the persisted entity.
     */
    public PcRatings update(PcRatings pcRatings) {
        log.debug("Request to update PcRatings : {}", pcRatings);
        return pcRatingsRepository.save(pcRatings);
    }

    /**
     * Partially update a pcRatings.
     *
     * @param pcRatings the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PcRatings> partialUpdate(PcRatings pcRatings) {
        log.debug("Request to partially update PcRatings : {}", pcRatings);

        return pcRatingsRepository
            .findById(pcRatings.getId())
            .map(existingPcRatings -> {
                if (pcRatings.getRating() != null) {
                    existingPcRatings.setRating(pcRatings.getRating());
                }
                if (pcRatings.getRatingContentType() != null) {
                    existingPcRatings.setRatingContentType(pcRatings.getRatingContentType());
                }
                if (pcRatings.getComment() != null) {
                    existingPcRatings.setComment(pcRatings.getComment());
                }
                if (pcRatings.getCommentContentType() != null) {
                    existingPcRatings.setCommentContentType(pcRatings.getCommentContentType());
                }
                if (pcRatings.getStausDate() != null) {
                    existingPcRatings.setStausDate(pcRatings.getStausDate());
                }
                if (pcRatings.getDueDate() != null) {
                    existingPcRatings.setDueDate(pcRatings.getDueDate());
                }
                if (pcRatings.getFreeze() != null) {
                    existingPcRatings.setFreeze(pcRatings.getFreeze());
                }
                if (pcRatings.getIncludeInFinalRating() != null) {
                    existingPcRatings.setIncludeInFinalRating(pcRatings.getIncludeInFinalRating());
                }
                if (pcRatings.getCreatedAt() != null) {
                    existingPcRatings.setCreatedAt(pcRatings.getCreatedAt());
                }
                if (pcRatings.getUpdatedAt() != null) {
                    existingPcRatings.setUpdatedAt(pcRatings.getUpdatedAt());
                }
                if (pcRatings.getDeletedAt() != null) {
                    existingPcRatings.setDeletedAt(pcRatings.getDeletedAt());
                }
                if (pcRatings.getVersion() != null) {
                    existingPcRatings.setVersion(pcRatings.getVersion());
                }

                return existingPcRatings;
            })
            .map(pcRatingsRepository::save);
    }

    /**
     * Get all the pcRatings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PcRatings> findAll(Pageable pageable) {
        log.debug("Request to get all PcRatings");
        return pcRatingsRepository.findAll(pageable);
    }

    /**
     * Get one pcRatings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PcRatings> findOne(Long id) {
        log.debug("Request to get PcRatings : {}", id);
        return pcRatingsRepository.findById(id);
    }

    /**
     * Delete the pcRatings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PcRatings : {}", id);
        pcRatingsRepository.deleteById(id);
    }
}
