package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.Interviews;
import com.venturedive.vendian_mono.repository.InterviewsRepository;
import com.venturedive.vendian_mono.service.InterviewsQueryService;
import com.venturedive.vendian_mono.service.InterviewsService;
import com.venturedive.vendian_mono.service.criteria.InterviewsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.Interviews}.
 */
@RestController
@RequestMapping("/api")
public class InterviewsResource {

    private final Logger log = LoggerFactory.getLogger(InterviewsResource.class);

    private static final String ENTITY_NAME = "interviews";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewsService interviewsService;

    private final InterviewsRepository interviewsRepository;

    private final InterviewsQueryService interviewsQueryService;

    public InterviewsResource(
        InterviewsService interviewsService,
        InterviewsRepository interviewsRepository,
        InterviewsQueryService interviewsQueryService
    ) {
        this.interviewsService = interviewsService;
        this.interviewsRepository = interviewsRepository;
        this.interviewsQueryService = interviewsQueryService;
    }

    /**
     * {@code POST  /interviews} : Create a new interviews.
     *
     * @param interviews the interviews to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviews, or with status {@code 400 (Bad Request)} if the interviews has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interviews")
    public ResponseEntity<Interviews> createInterviews(@Valid @RequestBody Interviews interviews) throws URISyntaxException {
        log.debug("REST request to save Interviews : {}", interviews);
        if (interviews.getId() != null) {
            throw new BadRequestAlertException("A new interviews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Interviews result = interviewsService.save(interviews);
        return ResponseEntity
            .created(new URI("/api/interviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interviews/:id} : Updates an existing interviews.
     *
     * @param id the id of the interviews to save.
     * @param interviews the interviews to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviews,
     * or with status {@code 400 (Bad Request)} if the interviews is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviews couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interviews/{id}")
    public ResponseEntity<Interviews> updateInterviews(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Interviews interviews
    ) throws URISyntaxException {
        log.debug("REST request to update Interviews : {}, {}", id, interviews);
        if (interviews.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interviews.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interviewsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Interviews result = interviewsService.update(interviews);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, interviews.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /interviews/:id} : Partial updates given fields of an existing interviews, field will ignore if it is null
     *
     * @param id the id of the interviews to save.
     * @param interviews the interviews to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviews,
     * or with status {@code 400 (Bad Request)} if the interviews is not valid,
     * or with status {@code 404 (Not Found)} if the interviews is not found,
     * or with status {@code 500 (Internal Server Error)} if the interviews couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/interviews/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Interviews> partialUpdateInterviews(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Interviews interviews
    ) throws URISyntaxException {
        log.debug("REST request to partial update Interviews partially : {}, {}", id, interviews);
        if (interviews.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interviews.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interviewsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Interviews> result = interviewsService.partialUpdate(interviews);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, interviews.getId().toString())
        );
    }

    /**
     * {@code GET  /interviews} : get all the interviews.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviews in body.
     */
    @GetMapping("/interviews")
    public ResponseEntity<List<Interviews>> getAllInterviews(
        InterviewsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Interviews by criteria: {}", criteria);
        Page<Interviews> page = interviewsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /interviews/count} : count all the interviews.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/interviews/count")
    public ResponseEntity<Long> countInterviews(InterviewsCriteria criteria) {
        log.debug("REST request to count Interviews by criteria: {}", criteria);
        return ResponseEntity.ok().body(interviewsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /interviews/:id} : get the "id" interviews.
     *
     * @param id the id of the interviews to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviews, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interviews/{id}")
    public ResponseEntity<Interviews> getInterviews(@PathVariable Long id) {
        log.debug("REST request to get Interviews : {}", id);
        Optional<Interviews> interviews = interviewsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interviews);
    }

    /**
     * {@code DELETE  /interviews/:id} : delete the "id" interviews.
     *
     * @param id the id of the interviews to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interviews/{id}")
    public ResponseEntity<Void> deleteInterviews(@PathVariable Long id) {
        log.debug("REST request to delete Interviews : {}", id);
        interviewsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
