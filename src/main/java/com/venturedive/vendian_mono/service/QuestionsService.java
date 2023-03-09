package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Questions;
import com.venturedive.vendian_mono.repository.QuestionsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Questions}.
 */
@Service
@Transactional
public class QuestionsService {

    private final Logger log = LoggerFactory.getLogger(QuestionsService.class);

    private final QuestionsRepository questionsRepository;

    public QuestionsService(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    /**
     * Save a questions.
     *
     * @param questions the entity to save.
     * @return the persisted entity.
     */
    public Questions save(Questions questions) {
        log.debug("Request to save Questions : {}", questions);
        return questionsRepository.save(questions);
    }

    /**
     * Update a questions.
     *
     * @param questions the entity to save.
     * @return the persisted entity.
     */
    public Questions update(Questions questions) {
        log.debug("Request to update Questions : {}", questions);
        return questionsRepository.save(questions);
    }

    /**
     * Partially update a questions.
     *
     * @param questions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Questions> partialUpdate(Questions questions) {
        log.debug("Request to partially update Questions : {}", questions);

        return questionsRepository
            .findById(questions.getId())
            .map(existingQuestions -> {
                if (questions.getText() != null) {
                    existingQuestions.setText(questions.getText());
                }
                if (questions.getAnswer() != null) {
                    existingQuestions.setAnswer(questions.getAnswer());
                }
                if (questions.getCreatedat() != null) {
                    existingQuestions.setCreatedat(questions.getCreatedat());
                }
                if (questions.getUpdatedat() != null) {
                    existingQuestions.setUpdatedat(questions.getUpdatedat());
                }
                if (questions.getDeletedat() != null) {
                    existingQuestions.setDeletedat(questions.getDeletedat());
                }
                if (questions.getCleaneduptext() != null) {
                    existingQuestions.setCleaneduptext(questions.getCleaneduptext());
                }

                return existingQuestions;
            })
            .map(questionsRepository::save);
    }

    /**
     * Get all the questions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Questions> findAll(Pageable pageable) {
        log.debug("Request to get all Questions");
        return questionsRepository.findAll(pageable);
    }

    /**
     * Get one questions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Questions> findOne(Long id) {
        log.debug("Request to get Questions : {}", id);
        return questionsRepository.findById(id);
    }

    /**
     * Delete the questions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Questions : {}", id);
        questionsRepository.deleteById(id);
    }
}
