package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.RatingAttributeMappings;
import com.venturedive.vendian_mono.repository.RatingAttributeMappingsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RatingAttributeMappings}.
 */
@Service
@Transactional
public class RatingAttributeMappingsService {

    private final Logger log = LoggerFactory.getLogger(RatingAttributeMappingsService.class);

    private final RatingAttributeMappingsRepository ratingAttributeMappingsRepository;

    public RatingAttributeMappingsService(RatingAttributeMappingsRepository ratingAttributeMappingsRepository) {
        this.ratingAttributeMappingsRepository = ratingAttributeMappingsRepository;
    }

    /**
     * Save a ratingAttributeMappings.
     *
     * @param ratingAttributeMappings the entity to save.
     * @return the persisted entity.
     */
    public RatingAttributeMappings save(RatingAttributeMappings ratingAttributeMappings) {
        log.debug("Request to save RatingAttributeMappings : {}", ratingAttributeMappings);
        return ratingAttributeMappingsRepository.save(ratingAttributeMappings);
    }

    /**
     * Update a ratingAttributeMappings.
     *
     * @param ratingAttributeMappings the entity to save.
     * @return the persisted entity.
     */
    public RatingAttributeMappings update(RatingAttributeMappings ratingAttributeMappings) {
        log.debug("Request to update RatingAttributeMappings : {}", ratingAttributeMappings);
        return ratingAttributeMappingsRepository.save(ratingAttributeMappings);
    }

    /**
     * Partially update a ratingAttributeMappings.
     *
     * @param ratingAttributeMappings the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RatingAttributeMappings> partialUpdate(RatingAttributeMappings ratingAttributeMappings) {
        log.debug("Request to partially update RatingAttributeMappings : {}", ratingAttributeMappings);

        return ratingAttributeMappingsRepository
            .findById(ratingAttributeMappings.getId())
            .map(existingRatingAttributeMappings -> {
                if (ratingAttributeMappings.getEffDate() != null) {
                    existingRatingAttributeMappings.setEffDate(ratingAttributeMappings.getEffDate());
                }
                if (ratingAttributeMappings.getCreatedAt() != null) {
                    existingRatingAttributeMappings.setCreatedAt(ratingAttributeMappings.getCreatedAt());
                }
                if (ratingAttributeMappings.getUpdatedAt() != null) {
                    existingRatingAttributeMappings.setUpdatedAt(ratingAttributeMappings.getUpdatedAt());
                }
                if (ratingAttributeMappings.getEndDate() != null) {
                    existingRatingAttributeMappings.setEndDate(ratingAttributeMappings.getEndDate());
                }
                if (ratingAttributeMappings.getVersion() != null) {
                    existingRatingAttributeMappings.setVersion(ratingAttributeMappings.getVersion());
                }

                return existingRatingAttributeMappings;
            })
            .map(ratingAttributeMappingsRepository::save);
    }

    /**
     * Get all the ratingAttributeMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RatingAttributeMappings> findAll(Pageable pageable) {
        log.debug("Request to get all RatingAttributeMappings");
        return ratingAttributeMappingsRepository.findAll(pageable);
    }

    /**
     * Get one ratingAttributeMappings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RatingAttributeMappings> findOne(Long id) {
        log.debug("Request to get RatingAttributeMappings : {}", id);
        return ratingAttributeMappingsRepository.findById(id);
    }

    /**
     * Delete the ratingAttributeMappings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RatingAttributeMappings : {}", id);
        ratingAttributeMappingsRepository.deleteById(id);
    }
}
