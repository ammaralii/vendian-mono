package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Ratings;
import com.venturedive.vendian_mono.repository.RatingsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ratings}.
 */
@Service
@Transactional
public class RatingsService {

    private final Logger log = LoggerFactory.getLogger(RatingsService.class);

    private final RatingsRepository ratingsRepository;

    public RatingsService(RatingsRepository ratingsRepository) {
        this.ratingsRepository = ratingsRepository;
    }

    /**
     * Save a ratings.
     *
     * @param ratings the entity to save.
     * @return the persisted entity.
     */
    public Ratings save(Ratings ratings) {
        log.debug("Request to save Ratings : {}", ratings);
        return ratingsRepository.save(ratings);
    }

    /**
     * Update a ratings.
     *
     * @param ratings the entity to save.
     * @return the persisted entity.
     */
    public Ratings update(Ratings ratings) {
        log.debug("Request to update Ratings : {}", ratings);
        return ratingsRepository.save(ratings);
    }

    /**
     * Partially update a ratings.
     *
     * @param ratings the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Ratings> partialUpdate(Ratings ratings) {
        log.debug("Request to partially update Ratings : {}", ratings);

        return ratingsRepository
            .findById(ratings.getId())
            .map(existingRatings -> {
                if (ratings.getRateeid() != null) {
                    existingRatings.setRateeid(ratings.getRateeid());
                }
                if (ratings.getRateetype() != null) {
                    existingRatings.setRateetype(ratings.getRateetype());
                }
                if (ratings.getDuedate() != null) {
                    existingRatings.setDuedate(ratings.getDuedate());
                }
                if (ratings.getFreeze() != null) {
                    existingRatings.setFreeze(ratings.getFreeze());
                }
                if (ratings.getFreezeContentType() != null) {
                    existingRatings.setFreezeContentType(ratings.getFreezeContentType());
                }
                if (ratings.getCreatedat() != null) {
                    existingRatings.setCreatedat(ratings.getCreatedat());
                }
                if (ratings.getUpdatedat() != null) {
                    existingRatings.setUpdatedat(ratings.getUpdatedat());
                }
                if (ratings.getDeletedat() != null) {
                    existingRatings.setDeletedat(ratings.getDeletedat());
                }

                return existingRatings;
            })
            .map(ratingsRepository::save);
    }

    /**
     * Get all the ratings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ratings> findAll(Pageable pageable) {
        log.debug("Request to get all Ratings");
        return ratingsRepository.findAll(pageable);
    }

    /**
     * Get one ratings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ratings> findOne(Long id) {
        log.debug("Request to get Ratings : {}", id);
        return ratingsRepository.findById(id);
    }

    /**
     * Delete the ratings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ratings : {}", id);
        ratingsRepository.deleteById(id);
    }
}
