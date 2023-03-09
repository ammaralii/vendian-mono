package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.QuestionsFrequencyPerClientTrack;
import com.venturedive.vendian_mono.repository.QuestionsFrequencyPerClientTrackRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QuestionsFrequencyPerClientTrack}.
 */
@Service
@Transactional
public class QuestionsFrequencyPerClientTrackService {

    private final Logger log = LoggerFactory.getLogger(QuestionsFrequencyPerClientTrackService.class);

    private final QuestionsFrequencyPerClientTrackRepository questionsFrequencyPerClientTrackRepository;

    public QuestionsFrequencyPerClientTrackService(QuestionsFrequencyPerClientTrackRepository questionsFrequencyPerClientTrackRepository) {
        this.questionsFrequencyPerClientTrackRepository = questionsFrequencyPerClientTrackRepository;
    }

    /**
     * Save a questionsFrequencyPerClientTrack.
     *
     * @param questionsFrequencyPerClientTrack the entity to save.
     * @return the persisted entity.
     */
    public QuestionsFrequencyPerClientTrack save(QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack) {
        log.debug("Request to save QuestionsFrequencyPerClientTrack : {}", questionsFrequencyPerClientTrack);
        return questionsFrequencyPerClientTrackRepository.save(questionsFrequencyPerClientTrack);
    }

    /**
     * Update a questionsFrequencyPerClientTrack.
     *
     * @param questionsFrequencyPerClientTrack the entity to save.
     * @return the persisted entity.
     */
    public QuestionsFrequencyPerClientTrack update(QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack) {
        log.debug("Request to update QuestionsFrequencyPerClientTrack : {}", questionsFrequencyPerClientTrack);
        return questionsFrequencyPerClientTrackRepository.save(questionsFrequencyPerClientTrack);
    }

    /**
     * Partially update a questionsFrequencyPerClientTrack.
     *
     * @param questionsFrequencyPerClientTrack the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QuestionsFrequencyPerClientTrack> partialUpdate(QuestionsFrequencyPerClientTrack questionsFrequencyPerClientTrack) {
        log.debug("Request to partially update QuestionsFrequencyPerClientTrack : {}", questionsFrequencyPerClientTrack);

        return questionsFrequencyPerClientTrackRepository
            .findById(questionsFrequencyPerClientTrack.getId())
            .map(existingQuestionsFrequencyPerClientTrack -> {
                if (questionsFrequencyPerClientTrack.getQuestion() != null) {
                    existingQuestionsFrequencyPerClientTrack.setQuestion(questionsFrequencyPerClientTrack.getQuestion());
                }
                if (questionsFrequencyPerClientTrack.getFrequency() != null) {
                    existingQuestionsFrequencyPerClientTrack.setFrequency(questionsFrequencyPerClientTrack.getFrequency());
                }
                if (questionsFrequencyPerClientTrack.getCreatedat() != null) {
                    existingQuestionsFrequencyPerClientTrack.setCreatedat(questionsFrequencyPerClientTrack.getCreatedat());
                }
                if (questionsFrequencyPerClientTrack.getUpdatedat() != null) {
                    existingQuestionsFrequencyPerClientTrack.setUpdatedat(questionsFrequencyPerClientTrack.getUpdatedat());
                }

                return existingQuestionsFrequencyPerClientTrack;
            })
            .map(questionsFrequencyPerClientTrackRepository::save);
    }

    /**
     * Get all the questionsFrequencyPerClientTracks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionsFrequencyPerClientTrack> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionsFrequencyPerClientTracks");
        return questionsFrequencyPerClientTrackRepository.findAll(pageable);
    }

    /**
     * Get one questionsFrequencyPerClientTrack by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QuestionsFrequencyPerClientTrack> findOne(Long id) {
        log.debug("Request to get QuestionsFrequencyPerClientTrack : {}", id);
        return questionsFrequencyPerClientTrackRepository.findById(id);
    }

    /**
     * Delete the questionsFrequencyPerClientTrack by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QuestionsFrequencyPerClientTrack : {}", id);
        questionsFrequencyPerClientTrackRepository.deleteById(id);
    }
}
