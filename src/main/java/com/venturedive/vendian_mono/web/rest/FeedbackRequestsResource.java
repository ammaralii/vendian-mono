package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.FeedbackRequests;
import com.venturedive.vendian_mono.repository.FeedbackRequestsRepository;
import com.venturedive.vendian_mono.service.FeedbackRequestsQueryService;
import com.venturedive.vendian_mono.service.FeedbackRequestsService;
import com.venturedive.vendian_mono.service.criteria.FeedbackRequestsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.FeedbackRequests}.
 */
@RestController
@RequestMapping("/api")
public class FeedbackRequestsResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackRequestsResource.class);

    private static final String ENTITY_NAME = "feedbackRequests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedbackRequestsService feedbackRequestsService;

    private final FeedbackRequestsRepository feedbackRequestsRepository;

    private final FeedbackRequestsQueryService feedbackRequestsQueryService;

    public FeedbackRequestsResource(
        FeedbackRequestsService feedbackRequestsService,
        FeedbackRequestsRepository feedbackRequestsRepository,
        FeedbackRequestsQueryService feedbackRequestsQueryService
    ) {
        this.feedbackRequestsService = feedbackRequestsService;
        this.feedbackRequestsRepository = feedbackRequestsRepository;
        this.feedbackRequestsQueryService = feedbackRequestsQueryService;
    }

    /**
     * {@code POST  /feedback-requests} : Create a new feedbackRequests.
     *
     * @param feedbackRequests the feedbackRequests to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedbackRequests, or with status {@code 400 (Bad Request)} if the feedbackRequests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedback-requests")
    public ResponseEntity<FeedbackRequests> createFeedbackRequests(@Valid @RequestBody FeedbackRequests feedbackRequests)
        throws URISyntaxException {
        log.debug("REST request to save FeedbackRequests : {}", feedbackRequests);
        if (feedbackRequests.getId() != null) {
            throw new BadRequestAlertException("A new feedbackRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedbackRequests result = feedbackRequestsService.save(feedbackRequests);
        return ResponseEntity
            .created(new URI("/api/feedback-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedback-requests/:id} : Updates an existing feedbackRequests.
     *
     * @param id the id of the feedbackRequests to save.
     * @param feedbackRequests the feedbackRequests to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackRequests,
     * or with status {@code 400 (Bad Request)} if the feedbackRequests is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedbackRequests couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedback-requests/{id}")
    public ResponseEntity<FeedbackRequests> updateFeedbackRequests(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeedbackRequests feedbackRequests
    ) throws URISyntaxException {
        log.debug("REST request to update FeedbackRequests : {}, {}", id, feedbackRequests);
        if (feedbackRequests.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackRequests.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackRequestsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeedbackRequests result = feedbackRequestsService.update(feedbackRequests);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackRequests.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feedback-requests/:id} : Partial updates given fields of an existing feedbackRequests, field will ignore if it is null
     *
     * @param id the id of the feedbackRequests to save.
     * @param feedbackRequests the feedbackRequests to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackRequests,
     * or with status {@code 400 (Bad Request)} if the feedbackRequests is not valid,
     * or with status {@code 404 (Not Found)} if the feedbackRequests is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedbackRequests couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feedback-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeedbackRequests> partialUpdateFeedbackRequests(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeedbackRequests feedbackRequests
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeedbackRequests partially : {}, {}", id, feedbackRequests);
        if (feedbackRequests.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackRequests.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackRequestsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeedbackRequests> result = feedbackRequestsService.partialUpdate(feedbackRequests);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackRequests.getId().toString())
        );
    }

    /**
     * {@code GET  /feedback-requests} : get all the feedbackRequests.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbackRequests in body.
     */
    @GetMapping("/feedback-requests")
    public ResponseEntity<List<FeedbackRequests>> getAllFeedbackRequests(
        FeedbackRequestsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FeedbackRequests by criteria: {}", criteria);
        Page<FeedbackRequests> page = feedbackRequestsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feedback-requests/count} : count all the feedbackRequests.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/feedback-requests/count")
    public ResponseEntity<Long> countFeedbackRequests(FeedbackRequestsCriteria criteria) {
        log.debug("REST request to count FeedbackRequests by criteria: {}", criteria);
        return ResponseEntity.ok().body(feedbackRequestsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feedback-requests/:id} : get the "id" feedbackRequests.
     *
     * @param id the id of the feedbackRequests to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedbackRequests, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedback-requests/{id}")
    public ResponseEntity<FeedbackRequests> getFeedbackRequests(@PathVariable Long id) {
        log.debug("REST request to get FeedbackRequests : {}", id);
        Optional<FeedbackRequests> feedbackRequests = feedbackRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedbackRequests);
    }

    /**
     * {@code DELETE  /feedback-requests/:id} : delete the "id" feedbackRequests.
     *
     * @param id the id of the feedbackRequests to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedback-requests/{id}")
    public ResponseEntity<Void> deleteFeedbackRequests(@PathVariable Long id) {
        log.debug("REST request to delete FeedbackRequests : {}", id);
        feedbackRequestsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
