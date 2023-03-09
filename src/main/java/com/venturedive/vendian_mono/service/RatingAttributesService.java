package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.RatingAttributes;
import com.venturedive.vendian_mono.repository.RatingAttributesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RatingAttributes}.
 */
@Service
@Transactional
public class RatingAttributesService {

    private final Logger log = LoggerFactory.getLogger(RatingAttributesService.class);

    private final RatingAttributesRepository ratingAttributesRepository;

    public RatingAttributesService(RatingAttributesRepository ratingAttributesRepository) {
        this.ratingAttributesRepository = ratingAttributesRepository;
    }

    /**
     * Save a ratingAttributes.
     *
     * @param ratingAttributes the entity to save.
     * @return the persisted entity.
     */
    public RatingAttributes save(RatingAttributes ratingAttributes) {
        log.debug("Request to save RatingAttributes : {}", ratingAttributes);
        return ratingAttributesRepository.save(ratingAttributes);
    }

    /**
     * Update a ratingAttributes.
     *
     * @param ratingAttributes the entity to save.
     * @return the persisted entity.
     */
    public RatingAttributes update(RatingAttributes ratingAttributes) {
        log.debug("Request to update RatingAttributes : {}", ratingAttributes);
        return ratingAttributesRepository.save(ratingAttributes);
    }

    /**
     * Partially update a ratingAttributes.
     *
     * @param ratingAttributes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RatingAttributes> partialUpdate(RatingAttributes ratingAttributes) {
        log.debug("Request to partially update RatingAttributes : {}", ratingAttributes);

        return ratingAttributesRepository
            .findById(ratingAttributes.getId())
            .map(existingRatingAttributes -> {
                if (ratingAttributes.getRaRating() != null) {
                    existingRatingAttributes.setRaRating(ratingAttributes.getRaRating());
                }
                if (ratingAttributes.getRaRatingContentType() != null) {
                    existingRatingAttributes.setRaRatingContentType(ratingAttributes.getRaRatingContentType());
                }
                if (ratingAttributes.getComment() != null) {
                    existingRatingAttributes.setComment(ratingAttributes.getComment());
                }
                if (ratingAttributes.getCommentContentType() != null) {
                    existingRatingAttributes.setCommentContentType(ratingAttributes.getCommentContentType());
                }
                if (ratingAttributes.getCreatedat() != null) {
                    existingRatingAttributes.setCreatedat(ratingAttributes.getCreatedat());
                }
                if (ratingAttributes.getUpdatedat() != null) {
                    existingRatingAttributes.setUpdatedat(ratingAttributes.getUpdatedat());
                }

                return existingRatingAttributes;
            })
            .map(ratingAttributesRepository::save);
    }

    /**
     * Get all the ratingAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RatingAttributes> findAll(Pageable pageable) {
        log.debug("Request to get all RatingAttributes");
        return ratingAttributesRepository.findAll(pageable);
    }

    /**
     * Get one ratingAttributes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RatingAttributes> findOne(Long id) {
        log.debug("Request to get RatingAttributes : {}", id);
        return ratingAttributesRepository.findById(id);
    }

    /**
     * Delete the ratingAttributes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RatingAttributes : {}", id);
        ratingAttributesRepository.deleteById(id);
    }
}
