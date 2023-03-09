package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.FeedbackResponses;
import com.venturedive.vendian_mono.repository.FeedbackResponsesRepository;
import com.venturedive.vendian_mono.service.FeedbackResponsesQueryService;
import com.venturedive.vendian_mono.service.FeedbackResponsesService;
import com.venturedive.vendian_mono.service.criteria.FeedbackResponsesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.FeedbackResponses}.
 */
@RestController
@RequestMapping("/api")
public class FeedbackResponsesResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackResponsesResource.class);

    private static final String ENTITY_NAME = "feedbackResponses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedbackResponsesService feedbackResponsesService;

    private final FeedbackResponsesRepository feedbackResponsesRepository;

    private final FeedbackResponsesQueryService feedbackResponsesQueryService;

    public FeedbackResponsesResource(
        FeedbackResponsesService feedbackResponsesService,
        FeedbackResponsesRepository feedbackResponsesRepository,
        FeedbackResponsesQueryService feedbackResponsesQueryService
    ) {
        this.feedbackResponsesService = feedbackResponsesService;
        this.feedbackResponsesRepository = feedbackResponsesRepository;
        this.feedbackResponsesQueryService = feedbackResponsesQueryService;
    }

    /**
     * {@code POST  /feedback-responses} : Create a new feedbackResponses.
     *
     * @param feedbackResponses the feedbackResponses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedbackResponses, or with status {@code 400 (Bad Request)} if the feedbackResponses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedback-responses")
    public ResponseEntity<FeedbackResponses> createFeedbackResponses(@Valid @RequestBody FeedbackResponses feedbackResponses)
        throws URISyntaxException {
        log.debug("REST request to save FeedbackResponses : {}", feedbackResponses);
        if (feedbackResponses.getId() != null) {
            throw new BadRequestAlertException("A new feedbackResponses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedbackResponses result = feedbackResponsesService.save(feedbackResponses);
        return ResponseEntity
            .created(new URI("/api/feedback-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedback-responses/:id} : Updates an existing feedbackResponses.
     *
     * @param id the id of the feedbackResponses to save.
     * @param feedbackResponses the feedbackResponses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackResponses,
     * or with status {@code 400 (Bad Request)} if the feedbackResponses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedbackResponses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedback-responses/{id}")
    public ResponseEntity<FeedbackResponses> updateFeedbackResponses(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeedbackResponses feedbackResponses
    ) throws URISyntaxException {
        log.debug("REST request to update FeedbackResponses : {}, {}", id, feedbackResponses);
        if (feedbackResponses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackResponses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackResponsesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeedbackResponses result = feedbackResponsesService.update(feedbackResponses);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackResponses.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feedback-responses/:id} : Partial updates given fields of an existing feedbackResponses, field will ignore if it is null
     *
     * @param id the id of the feedbackResponses to save.
     * @param feedbackResponses the feedbackResponses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackResponses,
     * or with status {@code 400 (Bad Request)} if the feedbackResponses is not valid,
     * or with status {@code 404 (Not Found)} if the feedbackResponses is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedbackResponses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feedback-responses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeedbackResponses> partialUpdateFeedbackResponses(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeedbackResponses feedbackResponses
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeedbackResponses partially : {}, {}", id, feedbackResponses);
        if (feedbackResponses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackResponses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackResponsesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeedbackResponses> result = feedbackResponsesService.partialUpdate(feedbackResponses);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackResponses.getId().toString())
        );
    }

    /**
     * {@code GET  /feedback-responses} : get all the feedbackResponses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbackResponses in body.
     */
    @GetMapping("/feedback-responses")
    public ResponseEntity<List<FeedbackResponses>> getAllFeedbackResponses(
        FeedbackResponsesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FeedbackResponses by criteria: {}", criteria);
        Page<FeedbackResponses> page = feedbackResponsesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feedback-responses/count} : count all the feedbackResponses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/feedback-responses/count")
    public ResponseEntity<Long> countFeedbackResponses(FeedbackResponsesCriteria criteria) {
        log.debug("REST request to count FeedbackResponses by criteria: {}", criteria);
        return ResponseEntity.ok().body(feedbackResponsesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feedback-responses/:id} : get the "id" feedbackResponses.
     *
     * @param id the id of the feedbackResponses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedbackResponses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedback-responses/{id}")
    public ResponseEntity<FeedbackResponses> getFeedbackResponses(@PathVariable Long id) {
        log.debug("REST request to get FeedbackResponses : {}", id);
        Optional<FeedbackResponses> feedbackResponses = feedbackResponsesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedbackResponses);
    }

    /**
     * {@code DELETE  /feedback-responses/:id} : delete the "id" feedbackResponses.
     *
     * @param id the id of the feedbackResponses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedback-responses/{id}")
    public ResponseEntity<Void> deleteFeedbackResponses(@PathVariable Long id) {
        log.debug("REST request to delete FeedbackResponses : {}", id);
        feedbackResponsesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
