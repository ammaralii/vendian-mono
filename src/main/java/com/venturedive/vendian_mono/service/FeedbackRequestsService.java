package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.FeedbackRequests;
import com.venturedive.vendian_mono.repository.FeedbackRequestsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeedbackRequests}.
 */
@Service
@Transactional
public class FeedbackRequestsService {

    private final Logger log = LoggerFactory.getLogger(FeedbackRequestsService.class);

    private final FeedbackRequestsRepository feedbackRequestsRepository;

    public FeedbackRequestsService(FeedbackRequestsRepository feedbackRequestsRepository) {
        this.feedbackRequestsRepository = feedbackRequestsRepository;
    }

    /**
     * Save a feedbackRequests.
     *
     * @param feedbackRequests the entity to save.
     * @return the persisted entity.
     */
    public FeedbackRequests save(FeedbackRequests feedbackRequests) {
        log.debug("Request to save FeedbackRequests : {}", feedbackRequests);
        return feedbackRequestsRepository.save(feedbackRequests);
    }

    /**
     * Update a feedbackRequests.
     *
     * @param feedbackRequests the entity to save.
     * @return the persisted entity.
     */
    public FeedbackRequests update(FeedbackRequests feedbackRequests) {
        log.debug("Request to update FeedbackRequests : {}", feedbackRequests);
        return feedbackRequestsRepository.save(feedbackRequests);
    }

    /**
     * Partially update a feedbackRequests.
     *
     * @param feedbackRequests the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FeedbackRequests> partialUpdate(FeedbackRequests feedbackRequests) {
        log.debug("Request to partially update FeedbackRequests : {}", feedbackRequests);

        return feedbackRequestsRepository
            .findById(feedbackRequests.getId())
            .map(existingFeedbackRequests -> {
                if (feedbackRequests.getStatus() != null) {
                    existingFeedbackRequests.setStatus(feedbackRequests.getStatus());
                }
                if (feedbackRequests.getIsreportavailable() != null) {
                    existingFeedbackRequests.setIsreportavailable(feedbackRequests.getIsreportavailable());
                }
                if (feedbackRequests.getReportpath() != null) {
                    existingFeedbackRequests.setReportpath(feedbackRequests.getReportpath());
                }
                if (feedbackRequests.getApprovedat() != null) {
                    existingFeedbackRequests.setApprovedat(feedbackRequests.getApprovedat());
                }
                if (feedbackRequests.getExpiredat() != null) {
                    existingFeedbackRequests.setExpiredat(feedbackRequests.getExpiredat());
                }
                if (feedbackRequests.getCreatedat() != null) {
                    existingFeedbackRequests.setCreatedat(feedbackRequests.getCreatedat());
                }
                if (feedbackRequests.getUpdatedat() != null) {
                    existingFeedbackRequests.setUpdatedat(feedbackRequests.getUpdatedat());
                }

                return existingFeedbackRequests;
            })
            .map(feedbackRequestsRepository::save);
    }

    /**
     * Get all the feedbackRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FeedbackRequests> findAll(Pageable pageable) {
        log.debug("Request to get all FeedbackRequests");
        return feedbackRequestsRepository.findAll(pageable);
    }

    /**
     * Get one feedbackRequests by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeedbackRequests> findOne(Long id) {
        log.debug("Request to get FeedbackRequests : {}", id);
        return feedbackRequestsRepository.findById(id);
    }

    /**
     * Delete the feedbackRequests by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FeedbackRequests : {}", id);
        feedbackRequestsRepository.deleteById(id);
    }
}
