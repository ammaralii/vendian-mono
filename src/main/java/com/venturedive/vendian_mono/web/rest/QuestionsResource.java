package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Questions;
import com.venturedive.vendian_mono.repository.QuestionsRepository;
import com.venturedive.vendian_mono.service.QuestionsQueryService;
import com.venturedive.vendian_mono.service.QuestionsService;
import com.venturedive.vendian_mono.service.criteria.QuestionsCriteria;
import com.venturedive.vendian_mono.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Questions}.
 */
@RestController
@RequestMapping("/api")
public class QuestionsResource {

    private final Logger log = LoggerFactory.getLogger(QuestionsResource.class);

    private static final String ENTITY_NAME = "questions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionsService questionsService;

    private final QuestionsRepository questionsRepository;

    private final QuestionsQueryService questionsQueryService;

    public QuestionsResource(
        QuestionsService questionsService,
        QuestionsRepository questionsRepository,
        QuestionsQueryService questionsQueryService
    ) {
        this.questionsService = questionsService;
        this.questionsRepository = questionsRepository;
        this.questionsQueryService = questionsQueryService;
    }

    /**
     * {@code POST  /questions} : Create a new questions.
     *
     * @param questions the questions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questions, or with status {@code 400 (Bad Request)} if the questions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/questions")
    public ResponseEntity<Questions> createQuestions(@Valid @RequestBody Questions questions) throws URISyntaxException {
        log.debug("REST request to save Questions : {}", questions);
        if (questions.getId() != null) {
            throw new BadRequestAlertException("A new questions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Questions result = questionsService.save(questions);
        return ResponseEntity
            .created(new URI("/api/questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /questions/:id} : Updates an existing questions.
     *
     * @param id the id of the questions to save.
     * @param questions the questions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questions,
     * or with status {@code 400 (Bad Request)} if the questions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/questions/{id}")
    public ResponseEntity<Questions> updateQuestions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Questions questions
    ) throws URISyntaxException {
        log.debug("REST request to update Questions : {}, {}", id, questions);
        if (questions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Questions result = questionsService.update(questions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /questions/:id} : Partial updates given fields of an existing questions, field will ignore if it is null
     *
     * @param id the id of the questions to save.
     * @param questions the questions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questions,
     * or with status {@code 400 (Bad Request)} if the questions is not valid,
     * or with status {@code 404 (Not Found)} if the questions is not found,
     * or with status {@code 500 (Internal Server Error)} if the questions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/questions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Questions> partialUpdateQuestions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Questions questions
    ) throws URISyntaxException {
        log.debug("REST request to partial update Questions partially : {}, {}", id, questions);
        if (questions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Questions> result = questionsService.partialUpdate(questions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questions.getId().toString())
        );
    }

    /**
     * {@code GET  /questions} : get all the questions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questions in body.
     */
    @GetMapping("/questions")
    public ResponseEntity<List<Questions>> getAllQuestions(
        QuestionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Questions by criteria: {}", criteria);
        Page<Questions> page = questionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /questions/count} : count all the questions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/questions/count")
    public ResponseEntity<Long> countQuestions(QuestionsCriteria criteria) {
        log.debug("REST request to count Questions by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /questions/:id} : get the "id" questions.
     *
     * @param id the id of the questions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/questions/{id}")
    public ResponseEntity<Questions> getQuestions(@PathVariable Long id) {
        log.debug("REST request to get Questions : {}", id);
        Optional<Questions> questions = questionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questions);
    }

    /**
     * {@code DELETE  /questions/:id} : delete the "id" questions.
     *
     * @param id the id of the questions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestions(@PathVariable Long id) {
        log.debug("REST request to delete Questions : {}", id);
        questionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
