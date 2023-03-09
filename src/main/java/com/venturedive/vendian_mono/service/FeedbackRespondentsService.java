package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.FeedbackRespondents;
import com.venturedive.vendian_mono.repository.FeedbackRespondentsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeedbackRespondents}.
 */
@Service
@Transactional
public class FeedbackRespondentsService {

    private final Logger log = LoggerFactory.getLogger(FeedbackRespondentsService.class);

    private final FeedbackRespondentsRepository feedbackRespondentsRepository;

    public FeedbackRespondentsService(FeedbackRespondentsRepository feedbackRespondentsRepository) {
        this.feedbackRespondentsRepository = feedbackRespondentsRepository;
    }

    /**
     * Save a feedbackRespondents.
     *
     * @param feedbackRespondents the entity to save.
     * @return the persisted entity.
     */
    public FeedbackRespondents save(FeedbackRespondents feedbackRespondents) {
        log.debug("Request to save FeedbackRespondents : {}", feedbackRespondents);
        return feedbackRespondentsRepository.save(feedbackRespondents);
    }

    /**
     * Update a feedbackRespondents.
     *
     * @param feedbackRespondents the entity to save.
     * @return the persisted entity.
     */
    public FeedbackRespondents update(FeedbackRespondents feedbackRespondents) {
        log.debug("Request to update FeedbackRespondents : {}", feedbackRespondents);
        return feedbackRespondentsRepository.save(feedbackRespondents);
    }

    /**
     * Partially update a feedbackRespondents.
     *
     * @param feedbackRespondents the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FeedbackRespondents> partialUpdate(FeedbackRespondents feedbackRespondents) {
        log.debug("Request to partially update FeedbackRespondents : {}", feedbackRespondents);

        return feedbackRespondentsRepository
            .findById(feedbackRespondents.getId())
            .map(existingFeedbackRespondents -> {
                if (feedbackRespondents.getCategory() != null) {
                    existingFeedbackRespondents.setCategory(feedbackRespondents.getCategory());
                }
                if (feedbackRespondents.getHasresponded() != null) {
                    existingFeedbackRespondents.setHasresponded(feedbackRespondents.getHasresponded());
                }
                if (feedbackRespondents.getRespondedat() != null) {
                    existingFeedbackRespondents.setRespondedat(feedbackRespondents.getRespondedat());
                }
                if (feedbackRespondents.getCreatedat() != null) {
                    existingFeedbackRespondents.setCreatedat(feedbackRespondents.getCreatedat());
                }
                if (feedbackRespondents.getUpdatedat() != null) {
                    existingFeedbackRespondents.setUpdatedat(feedbackRespondents.getUpdatedat());
                }

                return existingFeedbackRespondents;
            })
            .map(feedbackRespondentsRepository::save);
    }

    /**
     * Get all the feedbackRespondents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedbackRespondents> findAll(Pageable pageable) {
        log.debug("Request to get all FeedbackRespondents");
        return feedbackRespondentsRepository.findAll(pageable);
    }

    /**
     * Get one feedbackRespondents by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeedbackRespondents> findOne(Long id) {
        log.debug("Request to get FeedbackRespondents : {}", id);
        return feedbackRespondentsRepository.findById(id);
    }

    /**
     * Delete the feedbackRespondents by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FeedbackRespondents : {}", id);
        feedbackRespondentsRepository.deleteById(id);
    }
}
