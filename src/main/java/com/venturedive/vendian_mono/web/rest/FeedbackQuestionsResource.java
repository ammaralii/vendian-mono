package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.FeedbackQuestions;
import com.venturedive.vendian_mono.repository.FeedbackQuestionsRepository;
import com.venturedive.vendian_mono.service.FeedbackQuestionsQueryService;
import com.venturedive.vendian_mono.service.FeedbackQuestionsService;
import com.venturedive.vendian_mono.service.criteria.FeedbackQuestionsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.FeedbackQuestions}.
 */
@RestController
@RequestMapping("/api")
public class FeedbackQuestionsResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackQuestionsResource.class);

    private static final String ENTITY_NAME = "feedbackQuestions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedbackQuestionsService feedbackQuestionsService;

    private final FeedbackQuestionsRepository feedbackQuestionsRepository;

    private final FeedbackQuestionsQueryService feedbackQuestionsQueryService;

    public FeedbackQuestionsResource(
        FeedbackQuestionsService feedbackQuestionsService,
        FeedbackQuestionsRepository feedbackQuestionsRepository,
        FeedbackQuestionsQueryService feedbackQuestionsQueryService
    ) {
        this.feedbackQuestionsService = feedbackQuestionsService;
        this.feedbackQuestionsRepository = feedbackQuestionsRepository;
        this.feedbackQuestionsQueryService = feedbackQuestionsQueryService;
    }

    /**
     * {@code POST  /feedback-questions} : Create a new feedbackQuestions.
     *
     * @param feedbackQuestions the feedbackQuestions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedbackQuestions, or with status {@code 400 (Bad Request)} if the feedbackQuestions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedback-questions")
    public ResponseEntity<FeedbackQuestions> createFeedbackQuestions(@Valid @RequestBody FeedbackQuestions feedbackQuestions)
        throws URISyntaxException {
        log.debug("REST request to save FeedbackQuestions : {}", feedbackQuestions);
        if (feedbackQuestions.getId() != null) {
            throw new BadRequestAlertException("A new feedbackQuestions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedbackQuestions result = feedbackQuestionsService.save(feedbackQuestions);
        return ResponseEntity
            .created(new URI("/api/feedback-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedback-questions/:id} : Updates an existing feedbackQuestions.
     *
     * @param id the id of the feedbackQuestions to save.
     * @param feedbackQuestions the feedbackQuestions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackQuestions,
     * or with status {@code 400 (Bad Request)} if the feedbackQuestions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedbackQuestions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedback-questions/{id}")
    public ResponseEntity<FeedbackQuestions> updateFeedbackQuestions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeedbackQuestions feedbackQuestions
    ) throws URISyntaxException {
        log.debug("REST request to update FeedbackQuestions : {}, {}", id, feedbackQuestions);
        if (feedbackQuestions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackQuestions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackQuestionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeedbackQuestions result = feedbackQuestionsService.update(feedbackQuestions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackQuestions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feedback-questions/:id} : Partial updates given fields of an existing feedbackQuestions, field will ignore if it is null
     *
     * @param id the id of the feedbackQuestions to save.
     * @param feedbackQuestions the feedbackQuestions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackQuestions,
     * or with status {@code 400 (Bad Request)} if the feedbackQuestions is not valid,
     * or with status {@code 404 (Not Found)} if the feedbackQuestions is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedbackQuestions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feedback-questions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeedbackQuestions> partialUpdateFeedbackQuestions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeedbackQuestions feedbackQuestions
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeedbackQuestions partially : {}, {}", id, feedbackQuestions);
        if (feedbackQuestions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackQuestions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackQuestionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeedbackQuestions> result = feedbackQuestionsService.partialUpdate(feedbackQuestions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackQuestions.getId().toString())
        );
    }

    /**
     * {@code GET  /feedback-questions} : get all the feedbackQuestions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbackQuestions in body.
     */
    @GetMapping("/feedback-questions")
    public ResponseEntity<List<FeedbackQuestions>> getAllFeedbackQuestions(
        FeedbackQuestionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FeedbackQuestions by criteria: {}", criteria);
        Page<FeedbackQuestions> page = feedbackQuestionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feedback-questions/count} : count all the feedbackQuestions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/feedback-questions/count")
    public ResponseEntity<Long> countFeedbackQuestions(FeedbackQuestionsCriteria criteria) {
        log.debug("REST request to count FeedbackQuestions by criteria: {}", criteria);
        return ResponseEntity.ok().body(feedbackQuestionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feedback-questions/:id} : get the "id" feedbackQuestions.
     *
     * @param id the id of the feedbackQuestions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedbackQuestions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedback-questions/{id}")
    public ResponseEntity<FeedbackQuestions> getFeedbackQuestions(@PathVariable Long id) {
        log.debug("REST request to get FeedbackQuestions : {}", id);
        Optional<FeedbackQuestions> feedbackQuestions = feedbackQuestionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedbackQuestions);
    }

    /**
     * {@code DELETE  /feedback-questions/:id} : delete the "id" feedbackQuestions.
     *
     * @param id the id of the feedbackQuestions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedback-questions/{id}")
    public ResponseEntity<Void> deleteFeedbackQuestions(@PathVariable Long id) {
        log.debug("REST request to delete FeedbackQuestions : {}", id);
        feedbackQuestionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
