package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.RatingOptions;
import com.venturedive.vendian_mono.repository.RatingOptionsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RatingOptions}.
 */
@Service
@Transactional
public class RatingOptionsService {

    private final Logger log = LoggerFactory.getLogger(RatingOptionsService.class);

    private final RatingOptionsRepository ratingOptionsRepository;

    public RatingOptionsService(RatingOptionsRepository ratingOptionsRepository) {
        this.ratingOptionsRepository = ratingOptionsRepository;
    }

    /**
     * Save a ratingOptions.
     *
     * @param ratingOptions the entity to save.
     * @return the persisted entity.
     */
    public RatingOptions save(RatingOptions ratingOptions) {
        log.debug("Request to save RatingOptions : {}", ratingOptions);
        return ratingOptionsRepository.save(ratingOptions);
    }

    /**
     * Update a ratingOptions.
     *
     * @param ratingOptions the entity to save.
     * @return the persisted entity.
     */
    public RatingOptions update(RatingOptions ratingOptions) {
        log.debug("Request to update RatingOptions : {}", ratingOptions);
        return ratingOptionsRepository.save(ratingOptions);
    }

    /**
     * Partially update a ratingOptions.
     *
     * @param ratingOptions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RatingOptions> partialUpdate(RatingOptions ratingOptions) {
        log.debug("Request to partially update RatingOptions : {}", ratingOptions);

        return ratingOptionsRepository
            .findById(ratingOptions.getId())
            .map(existingRatingOptions -> {
                if (ratingOptions.getName() != null) {
                    existingRatingOptions.setName(ratingOptions.getName());
                }
                if (ratingOptions.getDescription() != null) {
                    existingRatingOptions.setDescription(ratingOptions.getDescription());
                }
                if (ratingOptions.getFrom() != null) {
                    existingRatingOptions.setFrom(ratingOptions.getFrom());
                }
                if (ratingOptions.getTo() != null) {
                    existingRatingOptions.setTo(ratingOptions.getTo());
                }
                if (ratingOptions.getEffDate() != null) {
                    existingRatingOptions.setEffDate(ratingOptions.getEffDate());
                }
                if (ratingOptions.getCreatedAt() != null) {
                    existingRatingOptions.setCreatedAt(ratingOptions.getCreatedAt());
                }
                if (ratingOptions.getUpdatedAt() != null) {
                    existingRatingOptions.setUpdatedAt(ratingOptions.getUpdatedAt());
                }
                if (ratingOptions.getEndDate() != null) {
                    existingRatingOptions.setEndDate(ratingOptions.getEndDate());
                }
                if (ratingOptions.getVersion() != null) {
                    existingRatingOptions.setVersion(ratingOptions.getVersion());
                }

                return existingRatingOptions;
            })
            .map(ratingOptionsRepository::save);
    }

    /**
     * Get all the ratingOptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RatingOptions> findAll(Pageable pageable) {
        log.debug("Request to get all RatingOptions");
        return ratingOptionsRepository.findAll(pageable);
    }

    /**
     * Get one ratingOptions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RatingOptions> findOne(Long id) {
        log.debug("Request to get RatingOptions : {}", id);
        return ratingOptionsRepository.findById(id);
    }

    /**
     * Delete the ratingOptions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RatingOptions : {}", id);
        ratingOptionsRepository.deleteById(id);
    }
}
