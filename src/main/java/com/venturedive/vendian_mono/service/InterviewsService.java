package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Interviews;
import com.venturedive.vendian_mono.repository.InterviewsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Interviews}.
 */
@Service
@Transactional
public class InterviewsService {

    private final Logger log = LoggerFactory.getLogger(InterviewsService.class);

    private final InterviewsRepository interviewsRepository;

    public InterviewsService(InterviewsRepository interviewsRepository) {
        this.interviewsRepository = interviewsRepository;
    }

    /**
     * Save a interviews.
     *
     * @param interviews the entity to save.
     * @return the persisted entity.
     */
    public Interviews save(Interviews interviews) {
        log.debug("Request to save Interviews : {}", interviews);
        return interviewsRepository.save(interviews);
    }

    /**
     * Update a interviews.
     *
     * @param interviews the entity to save.
     * @return the persisted entity.
     */
    public Interviews update(Interviews interviews) {
        log.debug("Request to update Interviews : {}", interviews);
        return interviewsRepository.save(interviews);
    }

    /**
     * Partially update a interviews.
     *
     * @param interviews the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Interviews> partialUpdate(Interviews interviews) {
        log.debug("Request to partially update Interviews : {}", interviews);

        return interviewsRepository
            .findById(interviews.getId())
            .map(existingInterviews -> {
                if (interviews.getResult() != null) {
                    existingInterviews.setResult(interviews.getResult());
                }
                if (interviews.getClientcomments() != null) {
                    existingInterviews.setClientcomments(interviews.getClientcomments());
                }
                if (interviews.getLmcomments() != null) {
                    existingInterviews.setLmcomments(interviews.getLmcomments());
                }
                if (interviews.getScheduledat() != null) {
                    existingInterviews.setScheduledat(interviews.getScheduledat());
                }
                if (interviews.getCreatedat() != null) {
                    existingInterviews.setCreatedat(interviews.getCreatedat());
                }
                if (interviews.getUpdatedat() != null) {
                    existingInterviews.setUpdatedat(interviews.getUpdatedat());
                }
                if (interviews.getDeletedat() != null) {
                    existingInterviews.setDeletedat(interviews.getDeletedat());
                }

                return existingInterviews;
            })
            .map(interviewsRepository::save);
    }

    /**
     * Get all the interviews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Interviews> findAll(Pageable pageable) {
        log.debug("Request to get all Interviews");
        return interviewsRepository.findAll(pageable);
    }

    /**
     * Get one interviews by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Interviews> findOne(Long id) {
        log.debug("Request to get Interviews : {}", id);
        return interviewsRepository.findById(id);
    }

    /**
     * Delete the interviews by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Interviews : {}", id);
        interviewsRepository.deleteById(id);
    }
}
