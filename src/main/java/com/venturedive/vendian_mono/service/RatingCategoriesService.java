package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.RatingCategories;
import com.venturedive.vendian_mono.repository.RatingCategoriesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RatingCategories}.
 */
@Service
@Transactional
public class RatingCategoriesService {

    private final Logger log = LoggerFactory.getLogger(RatingCategoriesService.class);

    private final RatingCategoriesRepository ratingCategoriesRepository;

    public RatingCategoriesService(RatingCategoriesRepository ratingCategoriesRepository) {
        this.ratingCategoriesRepository = ratingCategoriesRepository;
    }

    /**
     * Save a ratingCategories.
     *
     * @param ratingCategories the entity to save.
     * @return the persisted entity.
     */
    public RatingCategories save(RatingCategories ratingCategories) {
        log.debug("Request to save RatingCategories : {}", ratingCategories);
        return ratingCategoriesRepository.save(ratingCategories);
    }

    /**
     * Update a ratingCategories.
     *
     * @param ratingCategories the entity to save.
     * @return the persisted entity.
     */
    public RatingCategories update(RatingCategories ratingCategories) {
        log.debug("Request to update RatingCategories : {}", ratingCategories);
        return ratingCategoriesRepository.save(ratingCategories);
    }

    /**
     * Partially update a ratingCategories.
     *
     * @param ratingCategories the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RatingCategories> partialUpdate(RatingCategories ratingCategories) {
        log.debug("Request to partially update RatingCategories : {}", ratingCategories);

        return ratingCategoriesRepository
            .findById(ratingCategories.getId())
            .map(existingRatingCategories -> {
                if (ratingCategories.getName() != null) {
                    existingRatingCategories.setName(ratingCategories.getName());
                }
                if (ratingCategories.getEffDate() != null) {
                    existingRatingCategories.setEffDate(ratingCategories.getEffDate());
                }
                if (ratingCategories.getCreatedAt() != null) {
                    existingRatingCategories.setCreatedAt(ratingCategories.getCreatedAt());
                }
                if (ratingCategories.getUpdatedAt() != null) {
                    existingRatingCategories.setUpdatedAt(ratingCategories.getUpdatedAt());
                }
                if (ratingCategories.getEndDate() != null) {
                    existingRatingCategories.setEndDate(ratingCategories.getEndDate());
                }
                if (ratingCategories.getVersion() != null) {
                    existingRatingCategories.setVersion(ratingCategories.getVersion());
                }

                return existingRatingCategories;
            })
            .map(ratingCategoriesRepository::save);
    }

    /**
     * Get all the ratingCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RatingCategories> findAll(Pageable pageable) {
        log.debug("Request to get all RatingCategories");
        return ratingCategoriesRepository.findAll(pageable);
    }

    /**
     * Get one ratingCategories by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RatingCategories> findOne(Long id) {
        log.debug("Request to get RatingCategories : {}", id);
        return ratingCategoriesRepository.findById(id);
    }

    /**
     * Delete the ratingCategories by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RatingCategories : {}", id);
        ratingCategoriesRepository.deleteById(id);
    }
}
