package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.QuestionsProcessingEventLogs;
import com.venturedive.vendian_mono.repository.QuestionsProcessingEventLogsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QuestionsProcessingEventLogs}.
 */
@Service
@Transactional
public class QuestionsProcessingEventLogsService {

    private final Logger log = LoggerFactory.getLogger(QuestionsProcessingEventLogsService.class);

    private final QuestionsProcessingEventLogsRepository questionsProcessingEventLogsRepository;

    public QuestionsProcessingEventLogsService(QuestionsProcessingEventLogsRepository questionsProcessingEventLogsRepository) {
        this.questionsProcessingEventLogsRepository = questionsProcessingEventLogsRepository;
    }

    /**
     * Save a questionsProcessingEventLogs.
     *
     * @param questionsProcessingEventLogs the entity to save.
     * @return the persisted entity.
     */
    public QuestionsProcessingEventLogs save(QuestionsProcessingEventLogs questionsProcessingEventLogs) {
        log.debug("Request to save QuestionsProcessingEventLogs : {}", questionsProcessingEventLogs);
        return questionsProcessingEventLogsRepository.save(questionsProcessingEventLogs);
    }

    /**
     * Update a questionsProcessingEventLogs.
     *
     * @param questionsProcessingEventLogs the entity to save.
     * @return the persisted entity.
     */
    public QuestionsProcessingEventLogs update(QuestionsProcessingEventLogs questionsProcessingEventLogs) {
        log.debug("Request to update QuestionsProcessingEventLogs : {}", questionsProcessingEventLogs);
        return questionsProcessingEventLogsRepository.save(questionsProcessingEventLogs);
    }

    /**
     * Partially update a questionsProcessingEventLogs.
     *
     * @param questionsProcessingEventLogs the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QuestionsProcessingEventLogs> partialUpdate(QuestionsProcessingEventLogs questionsProcessingEventLogs) {
        log.debug("Request to partially update QuestionsProcessingEventLogs : {}", questionsProcessingEventLogs);

        return questionsProcessingEventLogsRepository
            .findById(questionsProcessingEventLogs.getId())
            .map(existingQuestionsProcessingEventLogs -> {
                if (questionsProcessingEventLogs.getProcessedOn() != null) {
                    existingQuestionsProcessingEventLogs.setProcessedOn(questionsProcessingEventLogs.getProcessedOn());
                }
                if (questionsProcessingEventLogs.getCreatedat() != null) {
                    existingQuestionsProcessingEventLogs.setCreatedat(questionsProcessingEventLogs.getCreatedat());
                }
                if (questionsProcessingEventLogs.getUpdatedat() != null) {
                    existingQuestionsProcessingEventLogs.setUpdatedat(questionsProcessingEventLogs.getUpdatedat());
                }

                return existingQuestionsProcessingEventLogs;
            })
            .map(questionsProcessingEventLogsRepository::save);
    }

    /**
     * Get all the questionsProcessingEventLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionsProcessingEventLogs> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionsProcessingEventLogs");
        return questionsProcessingEventLogsRepository.findAll(pageable);
    }

    /**
     * Get one questionsProcessingEventLogs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QuestionsProcessingEventLogs> findOne(Long id) {
        log.debug("Request to get QuestionsProcessingEventLogs : {}", id);
        return questionsProcessingEventLogsRepository.findById(id);
    }

    /**
     * Delete the questionsProcessingEventLogs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QuestionsProcessingEventLogs : {}", id);
        questionsProcessingEventLogsRepository.deleteById(id);
    }
}
