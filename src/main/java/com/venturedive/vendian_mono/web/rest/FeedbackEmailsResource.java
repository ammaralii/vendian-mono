package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.FeedbackEmails;
import com.venturedive.vendian_mono.repository.FeedbackEmailsRepository;
import com.venturedive.vendian_mono.service.FeedbackEmailsQueryService;
import com.venturedive.vendian_mono.service.FeedbackEmailsService;
import com.venturedive.vendian_mono.service.criteria.FeedbackEmailsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.FeedbackEmails}.
 */
@RestController
@RequestMapping("/api")
public class FeedbackEmailsResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackEmailsResource.class);

    private static final String ENTITY_NAME = "feedbackEmails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedbackEmailsService feedbackEmailsService;

    private final FeedbackEmailsRepository feedbackEmailsRepository;

    private final FeedbackEmailsQueryService feedbackEmailsQueryService;

    public FeedbackEmailsResource(
        FeedbackEmailsService feedbackEmailsService,
        FeedbackEmailsRepository feedbackEmailsRepository,
        FeedbackEmailsQueryService feedbackEmailsQueryService
    ) {
        this.feedbackEmailsService = feedbackEmailsService;
        this.feedbackEmailsRepository = feedbackEmailsRepository;
        this.feedbackEmailsQueryService = feedbackEmailsQueryService;
    }

    /**
     * {@code POST  /feedback-emails} : Create a new feedbackEmails.
     *
     * @param feedbackEmails the feedbackEmails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedbackEmails, or with status {@code 400 (Bad Request)} if the feedbackEmails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedback-emails")
    public ResponseEntity<FeedbackEmails> createFeedbackEmails(@Valid @RequestBody FeedbackEmails feedbackEmails)
        throws URISyntaxException {
        log.debug("REST request to save FeedbackEmails : {}", feedbackEmails);
        if (feedbackEmails.getId() != null) {
            throw new BadRequestAlertException("A new feedbackEmails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedbackEmails result = feedbackEmailsService.save(feedbackEmails);
        return ResponseEntity
            .created(new URI("/api/feedback-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedback-emails/:id} : Updates an existing feedbackEmails.
     *
     * @param id the id of the feedbackEmails to save.
     * @param feedbackEmails the feedbackEmails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackEmails,
     * or with status {@code 400 (Bad Request)} if the feedbackEmails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedbackEmails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedback-emails/{id}")
    public ResponseEntity<FeedbackEmails> updateFeedbackEmails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeedbackEmails feedbackEmails
    ) throws URISyntaxException {
        log.debug("REST request to update FeedbackEmails : {}, {}", id, feedbackEmails);
        if (feedbackEmails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackEmails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackEmailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeedbackEmails result = feedbackEmailsService.update(feedbackEmails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackEmails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feedback-emails/:id} : Partial updates given fields of an existing feedbackEmails, field will ignore if it is null
     *
     * @param id the id of the feedbackEmails to save.
     * @param feedbackEmails the feedbackEmails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackEmails,
     * or with status {@code 400 (Bad Request)} if the feedbackEmails is not valid,
     * or with status {@code 404 (Not Found)} if the feedbackEmails is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedbackEmails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feedback-emails/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeedbackEmails> partialUpdateFeedbackEmails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeedbackEmails feedbackEmails
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeedbackEmails partially : {}, {}", id, feedbackEmails);
        if (feedbackEmails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedbackEmails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackEmailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeedbackEmails> result = feedbackEmailsService.partialUpdate(feedbackEmails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedbackEmails.getId().toString())
        );
    }

    /**
     * {@code GET  /feedback-emails} : get all the feedbackEmails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbackEmails in body.
     */
    @GetMapping("/feedback-emails")
    public ResponseEntity<List<FeedbackEmails>> getAllFeedbackEmails(
        FeedbackEmailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FeedbackEmails by criteria: {}", criteria);
        Page<FeedbackEmails> page = feedbackEmailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feedback-emails/count} : count all the feedbackEmails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/feedback-emails/count")
    public ResponseEntity<Long> countFeedbackEmails(FeedbackEmailsCriteria criteria) {
        log.debug("REST request to count FeedbackEmails by criteria: {}", criteria);
        return ResponseEntity.ok().body(feedbackEmailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feedback-emails/:id} : get the "id" feedbackEmails.
     *
     * @param id the id of the feedbackEmails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedbackEmails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedback-emails/{id}")
    public ResponseEntity<FeedbackEmails> getFeedbackEmails(@PathVariable Long id) {
        log.debug("REST request to get FeedbackEmails : {}", id);
        Optional<FeedbackEmails> feedbackEmails = feedbackEmailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedbackEmails);
    }

    /**
     * {@code DELETE  /feedback-emails/:id} : delete the "id" feedbackEmails.
     *
     * @param id the id of the feedbackEmails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedback-emails/{id}")
    public ResponseEntity<Void> deleteFeedbackEmails(@PathVariable Long id) {
        log.debug("REST request to delete FeedbackEmails : {}", id);
        feedbackEmailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
