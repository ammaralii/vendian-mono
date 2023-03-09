package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.PerformanceCycleEmployeeRating;
import com.venturedive.vendian_mono.repository.PerformanceCycleEmployeeRatingRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PerformanceCycleEmployeeRating}.
 */
@Service
@Transactional
public class PerformanceCycleEmployeeRatingService {

    private final Logger log = LoggerFactory.getLogger(PerformanceCycleEmployeeRatingService.class);

    private final PerformanceCycleEmployeeRatingRepository performanceCycleEmployeeRatingRepository;

    public PerformanceCycleEmployeeRatingService(PerformanceCycleEmployeeRatingRepository performanceCycleEmployeeRatingRepository) {
        this.performanceCycleEmployeeRatingRepository = performanceCycleEmployeeRatingRepository;
    }

    /**
     * Save a performanceCycleEmployeeRating.
     *
     * @param performanceCycleEmployeeRating the entity to save.
     * @return the persisted entity.
     */
    public PerformanceCycleEmployeeRating save(PerformanceCycleEmployeeRating performanceCycleEmployeeRating) {
        log.debug("Request to save PerformanceCycleEmployeeRating : {}", performanceCycleEmployeeRating);
        return performanceCycleEmployeeRatingRepository.save(performanceCycleEmployeeRating);
    }

    /**
     * Update a performanceCycleEmployeeRating.
     *
     * @param performanceCycleEmployeeRating the entity to save.
     * @return the persisted entity.
     */
    public PerformanceCycleEmployeeRating update(PerformanceCycleEmployeeRating performanceCycleEmployeeRating) {
        log.debug("Request to update PerformanceCycleEmployeeRating : {}", performanceCycleEmployeeRating);
        return performanceCycleEmployeeRatingRepository.save(performanceCycleEmployeeRating);
    }

    /**
     * Partially update a performanceCycleEmployeeRating.
     *
     * @param performanceCycleEmployeeRating the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerformanceCycleEmployeeRating> partialUpdate(PerformanceCycleEmployeeRating performanceCycleEmployeeRating) {
        log.debug("Request to partially update PerformanceCycleEmployeeRating : {}", performanceCycleEmployeeRating);

        return performanceCycleEmployeeRatingRepository
            .findById(performanceCycleEmployeeRating.getId())
            .map(existingPerformanceCycleEmployeeRating -> {
                if (performanceCycleEmployeeRating.getRating() != null) {
                    existingPerformanceCycleEmployeeRating.setRating(performanceCycleEmployeeRating.getRating());
                }
                if (performanceCycleEmployeeRating.getRatingContentType() != null) {
                    existingPerformanceCycleEmployeeRating.setRatingContentType(performanceCycleEmployeeRating.getRatingContentType());
                }
                if (performanceCycleEmployeeRating.getComment() != null) {
                    existingPerformanceCycleEmployeeRating.setComment(performanceCycleEmployeeRating.getComment());
                }
                if (performanceCycleEmployeeRating.getCommentContentType() != null) {
                    existingPerformanceCycleEmployeeRating.setCommentContentType(performanceCycleEmployeeRating.getCommentContentType());
                }
                if (performanceCycleEmployeeRating.getCreatedAt() != null) {
                    existingPerformanceCycleEmployeeRating.setCreatedAt(performanceCycleEmployeeRating.getCreatedAt());
                }
                if (performanceCycleEmployeeRating.getUpdatedAt() != null) {
                    existingPerformanceCycleEmployeeRating.setUpdatedAt(performanceCycleEmployeeRating.getUpdatedAt());
                }
                if (performanceCycleEmployeeRating.getDeletedAt() != null) {
                    existingPerformanceCycleEmployeeRating.setDeletedAt(performanceCycleEmployeeRating.getDeletedAt());
                }
                if (performanceCycleEmployeeRating.getVersion() != null) {
                    existingPerformanceCycleEmployeeRating.setVersion(performanceCycleEmployeeRating.getVersion());
                }

                return existingPerformanceCycleEmployeeRating;
            })
            .map(performanceCycleEmployeeRatingRepository::save);
    }

    /**
     * Get all the performanceCycleEmployeeRatings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceCycleEmployeeRating> findAll(Pageable pageable) {
        log.debug("Request to get all PerformanceCycleEmployeeRatings");
        return performanceCycleEmployeeRatingRepository.findAll(pageable);
    }

    /**
     * Get one performanceCycleEmployeeRating by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerformanceCycleEmployeeRating> findOne(Long id) {
        log.debug("Request to get PerformanceCycleEmployeeRating : {}", id);
        return performanceCycleEmployeeRatingRepository.findById(id);
    }

    /**
     * Delete the performanceCycleEmployeeRating by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerformanceCycleEmployeeRating : {}", id);
        performanceCycleEmployeeRatingRepository.deleteById(id);
    }
}
