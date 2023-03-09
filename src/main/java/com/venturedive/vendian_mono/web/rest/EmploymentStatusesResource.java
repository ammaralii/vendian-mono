package com.venturedive.vendian_mono.web.rest;

import com.venturedive.vendian_mono.domain.EmploymentStatuses;
import com.venturedive.vendian_mono.repository.EmploymentStatusesRepository;
import com.venturedive.vendian_mono.service.EmploymentStatusesQueryService;
import com.venturedive.vendian_mono.service.EmploymentStatusesService;
import com.venturedive.vendian_mono.service.criteria.EmploymentStatusesCriteria;
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
 * REST controller for managing {@link com.venturedive.vendian_mono.domain.EmploymentStatuses}.
 */
@RestController
@RequestMapping("/api")
public class EmploymentStatusesResource {

    private final Logger log = LoggerFactory.getLogger(EmploymentStatusesResource.class);

    private static final String ENTITY_NAME = "employmentStatuses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmploymentStatusesService employmentStatusesService;

    private final EmploymentStatusesRepository employmentStatusesRepository;

    private final EmploymentStatusesQueryService employmentStatusesQueryService;

    public EmploymentStatusesResource(
        EmploymentStatusesService employmentStatusesService,
        EmploymentStatusesRepository employmentStatusesRepository,
        EmploymentStatusesQueryService employmentStatusesQueryService
    ) {
        this.employmentStatusesService = employmentStatusesService;
        this.employmentStatusesRepository = employmentStatusesRepository;
        this.employmentStatusesQueryService = employmentStatusesQueryService;
    }

    /**
     * {@code POST  /employment-statuses} : Create a new employmentStatuses.
     *
     * @param employmentStatuses the employmentStatuses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employmentStatuses, or with status {@code 400 (Bad Request)} if the employmentStatuses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employment-statuses")
    public ResponseEntity<EmploymentStatuses> createEmploymentStatuses(@Valid @RequestBody EmploymentStatuses employmentStatuses)
        throws URISyntaxException {
        log.debug("REST request to save EmploymentStatuses : {}", employmentStatuses);
        if (employmentStatuses.getId() != null) {
            throw new BadRequestAlertException("A new employmentStatuses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmploymentStatuses result = employmentStatusesService.save(employmentStatuses);
        return ResponseEntity
            .created(new URI("/api/employment-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employment-statuses/:id} : Updates an existing employmentStatuses.
     *
     * @param id the id of the employmentStatuses to save.
     * @param employmentStatuses the employmentStatuses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employmentStatuses,
     * or with status {@code 400 (Bad Request)} if the employmentStatuses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employmentStatuses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employment-statuses/{id}")
    public ResponseEntity<EmploymentStatuses> updateEmploymentStatuses(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmploymentStatuses employmentStatuses
    ) throws URISyntaxException {
        log.debug("REST request to update EmploymentStatuses : {}, {}", id, employmentStatuses);
        if (employmentStatuses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employmentStatuses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employmentStatusesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmploymentStatuses result = employmentStatusesService.update(employmentStatuses);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employmentStatuses.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employment-statuses/:id} : Partial updates given fields of an existing employmentStatuses, field will ignore if it is null
     *
     * @param id the id of the employmentStatuses to save.
     * @param employmentStatuses the employmentStatuses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employmentStatuses,
     * or with status {@code 400 (Bad Request)} if the employmentStatuses is not valid,
     * or with status {@code 404 (Not Found)} if the employmentStatuses is not found,
     * or with status {@code 500 (Internal Server Error)} if the employmentStatuses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employment-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmploymentStatuses> partialUpdateEmploymentStatuses(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmploymentStatuses employmentStatuses
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmploymentStatuses partially : {}, {}", id, employmentStatuses);
        if (employmentStatuses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employmentStatuses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employmentStatusesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmploymentStatuses> result = employmentStatusesService.partialUpdate(employmentStatuses);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employmentStatuses.getId().toString())
        );
    }

    /**
     * {@code GET  /employment-statuses} : get all the employmentStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employmentStatuses in body.
     */
    @GetMapping("/employment-statuses")
    public ResponseEntity<List<EmploymentStatuses>> getAllEmploymentStatuses(
        EmploymentStatusesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmploymentStatuses by criteria: {}", criteria);
        Page<EmploymentStatuses> page = employmentStatusesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employment-statuses/count} : count all the employmentStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employment-statuses/count")
    public ResponseEntity<Long> countEmploymentStatuses(EmploymentStatusesCriteria criteria) {
        log.debug("REST request to count EmploymentStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(employmentStatusesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employment-statuses/:id} : get the "id" employmentStatuses.
     *
     * @param id the id of the employmentStatuses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employmentStatuses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employment-statuses/{id}")
    public ResponseEntity<EmploymentStatuses> getEmploymentStatuses(@PathVariable Long id) {
        log.debug("REST request to get EmploymentStatuses : {}", id);
        Optional<EmploymentStatuses> employmentStatuses = employmentStatusesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employmentStatuses);
    }

    /**
     * {@code DELETE  /employment-statuses/:id} : delete the "id" employmentStatuses.
     *
     * @param id the id of the employmentStatuses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employment-statuses/{id}")
    public ResponseEntity<Void> deleteEmploymentStatuses(@PathVariable Long id) {
        log.debug("REST request to delete EmploymentStatuses : {}", id);
        employmentStatusesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
