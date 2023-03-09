package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.FeedbackResponses;
import com.venturedive.vendian_mono.repository.FeedbackResponsesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeedbackResponses}.
 */
@Service
@Transactional
public class FeedbackResponsesService {

    private final Logger log = LoggerFactory.getLogger(FeedbackResponsesService.class);

    private final FeedbackResponsesRepository feedbackResponsesRepository;

    public FeedbackResponsesService(FeedbackResponsesRepository feedbackResponsesRepository) {
        this.feedbackResponsesRepository = feedbackResponsesRepository;
    }

    /**
     * Save a feedbackResponses.
     *
     * @param feedbackResponses the entity to save.
     * @return the persisted entity.
     */
    public FeedbackResponses save(FeedbackResponses feedbackResponses) {
        log.debug("Request to save FeedbackResponses : {}", feedbackResponses);
        return feedbackResponsesRepository.save(feedbackResponses);
    }

    /**
     * Update a feedbackResponses.
     *
     * @param feedbackResponses the entity to save.
     * @return the persisted entity.
     */
    public FeedbackResponses update(FeedbackResponses feedbackResponses) {
        log.debug("Request to update FeedbackResponses : {}", feedbackResponses);
        return feedbackResponsesRepository.save(feedbackResponses);
    }

    /**
     * Partially update a feedbackResponses.
     *
     * @param feedbackResponses the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FeedbackResponses> partialUpdate(FeedbackResponses feedbackResponses) {
        log.debug("Request to partially update FeedbackResponses : {}", feedbackResponses);

        return feedbackResponsesRepository
            .findById(feedbackResponses.getId())
            .map(existingFeedbackResponses -> {
                if (feedbackResponses.getAnswer() != null) {
                    existingFeedbackResponses.setAnswer(feedbackResponses.getAnswer());
                }
                if (feedbackResponses.getAnswerContentType() != null) {
                    existingFeedbackResponses.setAnswerContentType(feedbackResponses.getAnswerContentType());
                }
                if (feedbackResponses.getRating() != null) {
                    existingFeedbackResponses.setRating(feedbackResponses.getRating());
                }
                if (feedbackResponses.getRatingContentType() != null) {
                    existingFeedbackResponses.setRatingContentType(feedbackResponses.getRatingContentType());
                }
                if (feedbackResponses.getCreatedat() != null) {
                    existingFeedbackResponses.setCreatedat(feedbackResponses.getCreatedat());
                }
                if (feedbackResponses.getUpdatedat() != null) {
                    existingFeedbackResponses.setUpdatedat(feedbackResponses.getUpdatedat());
                }

                return existingFeedbackResponses;
            })
            .map(feedbackResponsesRepository::save);
    }

    /**
     * Get all the feedbackResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedbackResponses> findAll(Pageable pageable) {
        log.debug("Request to get all FeedbackResponses");
        return feedbackResponsesRepository.findAll(pageable);
    }

    /**
     * Get one feedbackResponses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeedbackResponses> findOne(Long id) {
        log.debug("Request to get FeedbackResponses : {}", id);
        return feedbackResponsesRepository.findById(id);
    }

    /**
     * Delete the feedbackResponses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FeedbackResponses : {}", id);
        feedbackResponsesRepository.deleteById(id);
    }
}
