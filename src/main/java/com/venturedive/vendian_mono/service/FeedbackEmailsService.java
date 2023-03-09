package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.FeedbackEmails;
import com.venturedive.vendian_mono.repository.FeedbackEmailsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeedbackEmails}.
 */
@Service
@Transactional
public class FeedbackEmailsService {

    private final Logger log = LoggerFactory.getLogger(FeedbackEmailsService.class);

    private final FeedbackEmailsRepository feedbackEmailsRepository;

    public FeedbackEmailsService(FeedbackEmailsRepository feedbackEmailsRepository) {
        this.feedbackEmailsRepository = feedbackEmailsRepository;
    }

    /**
     * Save a feedbackEmails.
     *
     * @param feedbackEmails the entity to save.
     * @return the persisted entity.
     */
    public FeedbackEmails save(FeedbackEmails feedbackEmails) {
        log.debug("Request to save FeedbackEmails : {}", feedbackEmails);
        return feedbackEmailsRepository.save(feedbackEmails);
    }

    /**
     * Update a feedbackEmails.
     *
     * @param feedbackEmails the entity to save.
     * @return the persisted entity.
     */
    public FeedbackEmails update(FeedbackEmails feedbackEmails) {
        log.debug("Request to update FeedbackEmails : {}", feedbackEmails);
        return feedbackEmailsRepository.save(feedbackEmails);
    }

    /**
     * Partially update a feedbackEmails.
     *
     * @param feedbackEmails the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FeedbackEmails> partialUpdate(FeedbackEmails feedbackEmails) {
        log.debug("Request to partially update FeedbackEmails : {}", feedbackEmails);

        return feedbackEmailsRepository
            .findById(feedbackEmails.getId())
            .map(existingFeedbackEmails -> {
                if (feedbackEmails.getTo() != null) {
                    existingFeedbackEmails.setTo(feedbackEmails.getTo());
                }
                if (feedbackEmails.getBody() != null) {
                    existingFeedbackEmails.setBody(feedbackEmails.getBody());
                }
                if (feedbackEmails.getStatus() != null) {
                    existingFeedbackEmails.setStatus(feedbackEmails.getStatus());
                }
                if (feedbackEmails.getSentat() != null) {
                    existingFeedbackEmails.setSentat(feedbackEmails.getSentat());
                }
                if (feedbackEmails.getCreatedat() != null) {
                    existingFeedbackEmails.setCreatedat(feedbackEmails.getCreatedat());
                }
                if (feedbackEmails.getUpdatedat() != null) {
                    existingFeedbackEmails.setUpdatedat(feedbackEmails.getUpdatedat());
                }

                return existingFeedbackEmails;
            })
            .map(feedbackEmailsRepository::save);
    }

    /**
     * Get all the feedbackEmails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedbackEmails> findAll(Pageable pageable) {
        log.debug("Request to get all FeedbackEmails");
        return feedbackEmailsRepository.findAll(pageable);
    }

    /**
     * Get one feedbackEmails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeedbackEmails> findOne(Long id) {
        log.debug("Request to get FeedbackEmails : {}", id);
        return feedbackEmailsRepository.findById(id);
    }

    /**
     * Delete the feedbackEmails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FeedbackEmails : {}", id);
        feedbackEmailsRepository.deleteById(id);
    }
}
