package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.FeedbackRespondents;
import com.venturedive.vendian_mono.repository.FeedbackRespondentsRepository;
import com.venturedive.vendian_mono.service.FeedbackRespondentsQueryService;
import com.venturedive.vendian_mono.service.FeedbackRespondentsService;
import com.venturedive.vendian_mono.service.criteria.FeedbackRespondentsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.FeedbackRespondents}.
 */
@RestController
@RequestMapping("/api")
public class FeedbackRespondentsResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackRespondentsResource.class);

    private static final String ENTITY_NAME = "feedbackRespondents";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedbackRespondentsService feedbackRespondentsService;

    private final FeedbackRespondentsRepository feedbackRespondentsRepository;

    private final FeedbackRespondentsQueryService feedbackRespondentsQueryService;

    public FeedbackRespondentsResource(
        FeedbackRespondentsService feedbackRespondentsService,
        FeedbackRespondentsRepository feedbackRespondentsRepository,
        FeedbackRespondentsQueryService feedbackRespondentsQueryService
    ) {
        this.feedbackRespondentsService = feedbackRespondentsService;
        this.feedbackRespondentsRepository = feedbackRespondentsRepository;
        this.feedbackRespondentsQueryService = feedbackRespondentsQueryService;
    }

    /**
     * {@code POST  /feedback-respondents} : Create a new feedbackRespondents.
     *
     * @param feedbackRespondents the feedbackRespondents to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedbackRespondents, or with status {@code 400 (Bad Request)} if the feedbackRespondents has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedback-respondents")
    public ResponseEntity<FeedbackRespondents> createFeedbackRespondents(@Valid @RequestBody FeedbackRespondents feedbackRespondents)
        throws URISyntaxException {
        log.debug("REST request to save FeedbackRespondents : {}", feedbackRespondents);
        if (feedbackRespondents.getId() != null) {
            throw new BadRequestAlertException("A new feedbackRespondents cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedbackRespondents result = feedbackRespondentsService.save(feedbackRespondents);
        return ResponseEntity
            .created(new URI("/api/feedback-respondents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedback-respondents/:id} : Updates an existing feedbackRespondents.
     *
     * @param id the id of the feedbackRespondents to save.
     * @param feedbackRespondents the feedbackRespondents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackRespondents,
     * or with status {@code 400 (Bad Request)} if the feedbackRespondents is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedbackRespondents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedback-respondents/{id}")
    public ResponseEntity<FeedbackRespondents> updateFeedbackRespondents(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeedbackRespondents feedbackRespondents
    ) throws URISyntaxException {
        log.debug("REST request to update FeedbackRespondents : {}, {}", id, feedbackRespondents);
        if (feedbackRespondents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackRespondents.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackRespondentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeedbackRespondents result = feedbackRespondentsService.update(feedbackRespondents);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackRespondents.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feedback-respondents/:id} : Partial updates given fields of an existing feedbackRespondents, field will ignore if it is null
     *
     * @param id the id of the feedbackRespondents to save.
     * @param feedbackRespondents the feedbackRespondents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackRespondents,
     * or with status {@code 400 (Bad Request)} if the feedbackRespondents is not valid,
     * or with status {@code 404 (Not Found)} if the feedbackRespondents is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedbackRespondents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feedback-respondents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeedbackRespondents> partialUpdateFeedbackRespondents(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeedbackRespondents feedbackRespondents
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeedbackRespondents partially : {}, {}", id, feedbackRespondents);
        if (feedbackRespondents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackRespondents.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackRespondentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeedbackRespondents> result = feedbackRespondentsService.partialUpdate(feedbackRespondents);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackRespondents.getId().toString())
        );
    }

    /**
     * {@code GET  /feedback-respondents} : get all the feedbackRespondents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbackRespondents in body.
     */
    @GetMapping("/feedback-respondents")
    public ResponseEntity<List<FeedbackRespondents>> getAllFeedbackRespondents(
        FeedbackRespondentsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FeedbackRespondents by criteria: {}", criteria);
        Page<FeedbackRespondents> page = feedbackRespondentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feedback-respondents/count} : count all the feedbackRespondents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/feedback-respondents/count")
    public ResponseEntity<Long> countFeedbackRespondents(FeedbackRespondentsCriteria criteria) {
        log.debug("REST request to count FeedbackRespondents by criteria: {}", criteria);
        return ResponseEntity.ok().body(feedbackRespondentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feedback-respondents/:id} : get the "id" feedbackRespondents.
     *
     * @param id the id of the feedbackRespondents to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedbackRespondents, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedback-respondents/{id}")
    public ResponseEntity<FeedbackRespondents> getFeedbackRespondents(@PathVariable Long id) {
        log.debug("REST request to get FeedbackRespondents : {}", id);
        Optional<FeedbackRespondents> feedbackRespondents = feedbackRespondentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedbackRespondents);
    }

    /**
     * {@code DELETE  /feedback-respondents/:id} : delete the "id" feedbackRespondents.
     *
     * @param id the id of the feedbackRespondents to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedback-respondents/{id}")
    public ResponseEntity<Void> deleteFeedbackRespondents(@PathVariable Long id) {
        log.debug("REST request to delete FeedbackRespondents : {}", id);
        feedbackRespondentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
