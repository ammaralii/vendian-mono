package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.FeedbackQuestions;
import com.venturedive.vendian_mono.repository.FeedbackQuestionsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeedbackQuestions}.
 */
@Service
@Transactional
public class FeedbackQuestionsService {

    private final Logger log = LoggerFactory.getLogger(FeedbackQuestionsService.class);

    private final FeedbackQuestionsRepository feedbackQuestionsRepository;

    public FeedbackQuestionsService(FeedbackQuestionsRepository feedbackQuestionsRepository) {
        this.feedbackQuestionsRepository = feedbackQuestionsRepository;
    }

    /**
     * Save a feedbackQuestions.
     *
     * @param feedbackQuestions the entity to save.
     * @return the persisted entity.
     */
    public FeedbackQuestions save(FeedbackQuestions feedbackQuestions) {
        log.debug("Request to save FeedbackQuestions : {}", feedbackQuestions);
        return feedbackQuestionsRepository.save(feedbackQuestions);
    }

    /**
     * Update a feedbackQuestions.
     *
     * @param feedbackQuestions the entity to save.
     * @return the persisted entity.
     */
    public FeedbackQuestions update(FeedbackQuestions feedbackQuestions) {
        log.debug("Request to update FeedbackQuestions : {}", feedbackQuestions);
        return feedbackQuestionsRepository.save(feedbackQuestions);
    }

    /**
     * Partially update a feedbackQuestions.
     *
     * @param feedbackQuestions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FeedbackQuestions> partialUpdate(FeedbackQuestions feedbackQuestions) {
        log.debug("Request to partially update FeedbackQuestions : {}", feedbackQuestions);

        return feedbackQuestionsRepository
            .findById(feedbackQuestions.getId())
            .map(existingFeedbackQuestions -> {
                if (feedbackQuestions.getQuestion() != null) {
                    existingFeedbackQuestions.setQuestion(feedbackQuestions.getQuestion());
                }
                if (feedbackQuestions.getIsdefault() != null) {
                    existingFeedbackQuestions.setIsdefault(feedbackQuestions.getIsdefault());
                }
                if (feedbackQuestions.getArea() != null) {
                    existingFeedbackQuestions.setArea(feedbackQuestions.getArea());
                }
                if (feedbackQuestions.getCompetency() != null) {
                    existingFeedbackQuestions.setCompetency(feedbackQuestions.getCompetency());
                }
                if (feedbackQuestions.getCategory() != null) {
                    existingFeedbackQuestions.setCategory(feedbackQuestions.getCategory());
                }
                if (feedbackQuestions.getIsskill() != null) {
                    existingFeedbackQuestions.setIsskill(feedbackQuestions.getIsskill());
                }
                if (feedbackQuestions.getSkilltype() != null) {
                    existingFeedbackQuestions.setSkilltype(feedbackQuestions.getSkilltype());
                }
                if (feedbackQuestions.getCreatedat() != null) {
                    existingFeedbackQuestions.setCreatedat(feedbackQuestions.getCreatedat());
                }
                if (feedbackQuestions.getUpdatedat() != null) {
                    existingFeedbackQuestions.setUpdatedat(feedbackQuestions.getUpdatedat());
                }

                return existingFeedbackQuestions;
            })
            .map(feedbackQuestionsRepository::save);
    }

    /**
     * Get all the feedbackQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedbackQuestions> findAll(Pageable pageable) {
        log.debug("Request to get all FeedbackQuestions");
        return feedbackQuestionsRepository.findAll(pageable);
    }

    /**
     * Get one feedbackQuestions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeedbackQuestions> findOne(Long id) {
        log.debug("Request to get FeedbackQuestions : {}", id);
        return feedbackQuestionsRepository.findById(id);
    }

    /**
     * Delete the feedbackQuestions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FeedbackQuestions : {}", id);
        feedbackQuestionsRepository.deleteById(id);
    }
}
