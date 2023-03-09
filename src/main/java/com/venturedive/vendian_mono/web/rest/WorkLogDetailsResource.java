package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.WorkLogDetails;
import com.venturedive.vendian_mono.repository.WorkLogDetailsRepository;
import com.venturedive.vendian_mono.service.WorkLogDetailsQueryService;
import com.venturedive.vendian_mono.service.WorkLogDetailsService;
import com.venturedive.vendian_mono.service.criteria.WorkLogDetailsCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.WorkLogDetails}.
 */
@RestController
@RequestMapping("/api")
public class WorkLogDetailsResource {

    private final Logger log = LoggerFactory.getLogger(WorkLogDetailsResource.class);

    private static final String ENTITY_NAME = "workLogDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkLogDetailsService workLogDetailsService;

    private final WorkLogDetailsRepository workLogDetailsRepository;

    private final WorkLogDetailsQueryService workLogDetailsQueryService;

    public WorkLogDetailsResource(
        WorkLogDetailsService workLogDetailsService,
        WorkLogDetailsRepository workLogDetailsRepository,
        WorkLogDetailsQueryService workLogDetailsQueryService
    ) {
        this.workLogDetailsService = workLogDetailsService;
        this.workLogDetailsRepository = workLogDetailsRepository;
        this.workLogDetailsQueryService = workLogDetailsQueryService;
    }

    /**
     * {@code POST  /work-log-details} : Create a new workLogDetails.
     *
     * @param workLogDetails the workLogDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workLogDetails, or with status {@code 400 (Bad Request)} if the workLogDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-log-details")
    public ResponseEntity<WorkLogDetails> createWorkLogDetails(@Valid @RequestBody WorkLogDetails workLogDetails)
        throws URISyntaxException {
        log.debug("REST request to save WorkLogDetails : {}", workLogDetails);
        if (workLogDetails.getId() != null) {
            throw new BadRequestAlertException("A new workLogDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkLogDetails result = workLogDetailsService.save(workLogDetails);
        return ResponseEntity
            .created(new URI("/api/work-log-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-log-details/:id} : Updates an existing workLogDetails.
     *
     * @param id the id of the workLogDetails to save.
     * @param workLogDetails the workLogDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workLogDetails,
     * or with status {@code 400 (Bad Request)} if the workLogDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workLogDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-log-details/{id}")
    public ResponseEntity<WorkLogDetails> updateWorkLogDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkLogDetails workLogDetails
    ) throws URISyntaxException {
        log.debug("REST request to update WorkLogDetails : {}, {}", id, workLogDetails);
        if (workLogDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workLogDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workLogDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkLogDetails result = workLogDetailsService.update(workLogDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workLogDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /work-log-details/:id} : Partial updates given fields of an existing workLogDetails, field will ignore if it is null
     *
     * @param id the id of the workLogDetails to save.
     * @param workLogDetails the workLogDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workLogDetails,
     * or with status {@code 400 (Bad Request)} if the workLogDetails is not valid,
     * or with status {@code 404 (Not Found)} if the workLogDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the workLogDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/work-log-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkLogDetails> partialUpdateWorkLogDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WorkLogDetails workLogDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkLogDetails partially : {}, {}", id, workLogDetails);
        if (workLogDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workLogDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workLogDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkLogDetails> result = workLogDetailsService.partialUpdate(workLogDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workLogDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /work-log-details} : get all the workLogDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workLogDetails in body.
     */
    @GetMapping("/work-log-details")
    public ResponseEntity<List<WorkLogDetails>> getAllWorkLogDetails(
        WorkLogDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get WorkLogDetails by criteria: {}", criteria);
        Page<WorkLogDetails> page = workLogDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-log-details/count} : count all the workLogDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/work-log-details/count")
    public ResponseEntity<Long> countWorkLogDetails(WorkLogDetailsCriteria criteria) {
        log.debug("REST request to count WorkLogDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(workLogDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-log-details/:id} : get the "id" workLogDetails.
     *
     * @param id the id of the workLogDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workLogDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-log-details/{id}")
    public ResponseEntity<WorkLogDetails> getWorkLogDetails(@PathVariable Long id) {
        log.debug("REST request to get WorkLogDetails : {}", id);
        Optional<WorkLogDetails> workLogDetails = workLogDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workLogDetails);
    }

    /**
     * {@code DELETE  /work-log-details/:id} : delete the "id" workLogDetails.
     *
     * @param id the id of the workLogDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-log-details/{id}")
    public ResponseEntity<Void> deleteWorkLogDetails(@PathVariable Long id) {
        log.debug("REST request to delete WorkLogDetails : {}", id);
        workLogDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
