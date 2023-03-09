package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerTrack;
import com.venturedive.vendian_mono.repository.QuestionsFrequencyPerTrackRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QuestionsFrequencyPerTrack}.
 */
@Service
@Transactional
public class QuestionsFrequencyPerTrackService {

    private final Logger log = LoggerFactory.getLogger(QuestionsFrequencyPerTrackService.class);

    private final QuestionsFrequencyPerTrackRepository questionsFrequencyPerTrackRepository;

    public QuestionsFrequencyPerTrackService(QuestionsFrequencyPerTrackRepository questionsFrequencyPerTrackRepository) {
        this.questionsFrequencyPerTrackRepository = questionsFrequencyPerTrackRepository;
    }

    /**
     * Save a questionsFrequencyPerTrack.
     *
     * @param questionsFrequencyPerTrack the entity to save.
     * @return the persisted entity.
     */
    public QuestionsFrequencyPerTrack save(QuestionsFrequencyPerTrack questionsFrequencyPerTrack) {
        log.debug("Request to save QuestionsFrequencyPerTrack : {}", questionsFrequencyPerTrack);
        return questionsFrequencyPerTrackRepository.save(questionsFrequencyPerTrack);
    }

    /**
     * Update a questionsFrequencyPerTrack.
     *
     * @param questionsFrequencyPerTrack the entity to save.
     * @return the persisted entity.
     */
    public QuestionsFrequencyPerTrack update(QuestionsFrequencyPerTrack questionsFrequencyPerTrack) {
        log.debug("Request to update QuestionsFrequencyPerTrack : {}", questionsFrequencyPerTrack);
        return questionsFrequencyPerTrackRepository.save(questionsFrequencyPerTrack);
    }

    /**
     * Partially update a questionsFrequencyPerTrack.
     *
     * @param questionsFrequencyPerTrack the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QuestionsFrequencyPerTrack> partialUpdate(QuestionsFrequencyPerTrack questionsFrequencyPerTrack) {
        log.debug("Request to partially update QuestionsFrequencyPerTrack : {}", questionsFrequencyPerTrack);

        return questionsFrequencyPerTrackRepository
            .findById(questionsFrequencyPerTrack.getId())
            .map(existingQuestionsFrequencyPerTrack -> {
                if (questionsFrequencyPerTrack.getQuestion() != null) {
                    existingQuestionsFrequencyPerTrack.setQuestion(questionsFrequencyPerTrack.getQuestion());
                }
                if (questionsFrequencyPerTrack.getFrequency() != null) {
                    existingQuestionsFrequencyPerTrack.setFrequency(questionsFrequencyPerTrack.getFrequency());
                }
                if (questionsFrequencyPerTrack.getCreatedat() != null) {
                    existingQuestionsFrequencyPerTrack.setCreatedat(questionsFrequencyPerTrack.getCreatedat());
                }
                if (questionsFrequencyPerTrack.getUpdatedat() != null) {
                    existingQuestionsFrequencyPerTrack.setUpdatedat(questionsFrequencyPerTrack.getUpdatedat());
                }

                return existingQuestionsFrequencyPerTrack;
            })
            .map(questionsFrequencyPerTrackRepository::save);
    }

    /**
     * Get all the questionsFrequencyPerTracks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionsFrequencyPerTrack> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionsFrequencyPerTracks");
        return questionsFrequencyPerTrackRepository.findAll(pageable);
    }

    /**
     * Get one questionsFrequencyPerTrack by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QuestionsFrequencyPerTrack> findOne(Long id) {
        log.debug("Request to get QuestionsFrequencyPerTrack : {}", id);
        return questionsFrequencyPerTrackRepository.findById(id);
    }

    /**
     * Delete the questionsFrequencyPerTrack by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QuestionsFrequencyPerTrack : {}", id);
        questionsFrequencyPerTrackRepository.deleteById(id);
    }
}
